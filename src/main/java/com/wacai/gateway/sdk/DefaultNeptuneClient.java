package com.wacai.gateway.sdk;


import com.wacai.gateway.sdk.common.Assert;
import com.wacai.gateway.sdk.common.TypeReference;
import com.wacai.gateway.sdk.encrypt.rsa.RSAMode;
import com.wacai.gateway.sdk.extension.ExtensionLoader;
import com.wacai.gateway.sdk.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * @author bairen
 *
 */
class DefaultNeptuneClient implements NeptuneClient {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNeptuneClient.class);

    private NeptuneConfig config;

    private Invoker invoker;

    public void init(NeptuneConfig config) {
        checkConfig(config);
        this.config = config;
        this.invoker = loadInvoker(config);

    }

    @Override
    public ApiResponse invoke(ApiRequest request) throws NeptuneException {

        NeptuneConfig config = mergeNeptuneConfig(this.config, request);

        List<Filter> filters = loadFilters();

        Invoker filterInvoker = FilterInvokerBuilder.build(invoker, filters);

        Invocation invocation = populateInvocation(request);

        try {

            Result result = filterInvoker.invoke(invocation);
            ApiResponse response = new ApiResponse();
            response.setCode(result.getCode());
            response.setData(result.getData());
            response.setHeaders(result.getHeaders());
            return response;

        } catch (NeptuneException e) {
            LOG.error("neptune invoke error. appKey:" + config.getAppKey() + ",api:" + request.getApiName(), e);
            throw e;
        }catch (Throwable e) {
            LOG.error("neptune invoke error. appKey:" + config.getAppKey() + ",api:" + request.getApiName(), e);
            throw new NeptuneException(e);
        }
    }

    @Override
    public <T> T invoke(ApiRequest request, Class<T> clazz) throws NeptuneException {
        ApiResponse apiResponse = this.invoke(request);
        Mapper mapper = loadMapper(config);
        return mapper.read(new String(apiResponse.getData()), clazz);
    }

    @Override
    public <T> T invoke(ApiRequest request, TypeReference<T> typeReference) throws NeptuneException {
        ApiResponse apiResponse = this.invoke(request);
        Mapper mapper = loadMapper(config);
        return mapper.read(new String(apiResponse.getData()), typeReference.getType());
    }

    /**
     *
     * @param config
     */
    protected void checkConfig(NeptuneConfig config) {
        Assert.notNull(config.getHost(), "host is null");
        Assert.notNull(config.getAppKey(), "appKey is null");
        Assert.notNull(config.getAppSecret(), "secret is null");
    }

    /**
     *
     * @param config
     * @param request
     * @return
     */
    protected NeptuneConfig mergeNeptuneConfig(NeptuneConfig config, ApiRequest request) {
        if(config.getMode()==null) {
            config.setMode(RSAMode.getClientMode());
        }
        return config;
    }

    /**
     * load invoker
     * @param config
     * @return
     */
    protected Invoker loadInvoker(NeptuneConfig config) {
        return ExtensionLoader
                .getExtensionLoader(InvokerFactory.class)
                .getExtension(config.getInvoker())
                .getInvoker(config);
    }

    /**
     * load filters
     * @return
     */
    protected List<Filter> loadFilters() {
        return ExtensionLoader
                .getExtensionLoader(Filter.class)
                .getExtensions();
    }

    /**
     * populate invocation
     * @param request
     * @return
     */
    protected Invocation populateInvocation(ApiRequest request) {
        Assert.notNull(request.getHeaders(), "headers == null");

        ApiInvocation invocation = new ApiInvocation();
        String url = String.format("%s/%s/%s", config.getHost(), request.getApiName(), request.getApiVersion());
        invocation.setUrl(url);
        invocation.setApiVersion(request.getApiVersion());
        invocation.setApiName(request.getApiName());
        invocation.setData(request.getData());
        invocation.setHeaders(request.getHeaders());

        return invocation;
    }

    /**
     * load mapper
     * @return
     */
    protected Mapper loadMapper(NeptuneConfig config) {
        return ExtensionLoader
                .getExtensionLoader(Mapper.class)
                .getExtension(config.getMapper());
    }

    @Override
    public void shutdown() {
        invoker.close();
    }

}
