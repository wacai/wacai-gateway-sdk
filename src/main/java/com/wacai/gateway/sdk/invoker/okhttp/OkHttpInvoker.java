package com.wacai.gateway.sdk.invoker.okhttp;

import com.wacai.gateway.sdk.common.*;
import com.wacai.gateway.sdk.Invocation;
import com.wacai.gateway.sdk.NeptuneConfig;
import com.wacai.gateway.sdk.NeptuneException;
import com.wacai.gateway.sdk.Result;
import com.wacai.gateway.sdk.invoker.AbstractInvoker;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.wacai.gateway.sdk.contract.Headers.*;


/**
 * @author bairen
 */
@Slf4j
public class OkHttpInvoker extends AbstractInvoker {

    private static final Map<String, MediaType> media_type_map = new HashMap<String, MediaType>();
    private static final MediaType json = MediaType.parse(CONTENT_TYPE_JSON);
    private static final MediaType binary = MediaType.parse(CONTENT_TYPE_BINARY);

    static {
        media_type_map.put(CONTENT_TYPE_JSON, json);
        media_type_map.put(CONTENT_TYPE_BINARY, binary);
    }

    private OkHttpClient client;

    public OkHttpInvoker(NeptuneConfig config) {
        super(config);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        ConnectionPool pool = new ConnectionPool(200, 1, TimeUnit.MINUTES);
        builder
                .connectionPool(pool)
                .callTimeout(config.getTimeoutMs(), TimeUnit.MILLISECONDS)
                //如果不设置读取时间，默认10秒读取超时，就存在用户设置了最大耗时30秒，实际10秒就超了
                .readTimeout(config.getTimeoutMs(), TimeUnit.MILLISECONDS);
        client = builder.build();

    }

    @Override
    public Result invoke(Invocation invocation) throws NeptuneException {

        Request request = null;
        OkHttpClient client = this.client;

        MediaType mediaType = media_type_map.get(invocation.getHeaders().get(CONTENT_TYPE));
        if (mediaType == null) {
            mediaType = json;
        }
        RequestBody body = RequestBody.create(mediaType, invocation.getData());
        String url = invocation.getUrl();
        request = new Request.Builder()
                .url(url)
                .headers(parseHeaderMap(invocation.getHeaders()))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Result result = new Result();
            result.setData(response.body().bytes());
            result.setCode(response.code());
            result.setHeaders(parseHeaders(response.headers()));
            return result;
        } catch (InterruptedIOException e) {
            String msg = String.format("invoke timeout! url:%s", invocation.getUrl());
            throw new NeptuneException(msg, e);
        } catch (IOException e) {
            String msg = String.format("invoke io error! url:%s", invocation.getUrl());
            throw new NeptuneException(msg, e);
        }
    }

    protected Map<String, String> parseHeaders(Headers sources) {
        Map<String, String> ret = CollectionUtils.newKeyCaseInsensitiveMap();

        sources.names().stream().forEach(
                i -> {
                    ret.put(i, sources.get(i));
                });

        return ret;
    }

    protected Headers parseHeaderMap(Map<String, String> sources) {

        Headers.Builder builder = new Headers.Builder();
        sources.entrySet().stream().forEach(
                e -> {
                    builder.add(e.getKey(), e.getValue());
                });
        return builder.build();
    }

    public void close() {
        try {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
            client.cache().close();
        } catch (IOException e) {
            log.error("close okhttp error", e);
        }
    }
}
