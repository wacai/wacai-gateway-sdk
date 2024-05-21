package com.wacai.gateway.sdk;


import com.wacai.gateway.sdk.common.Assert;

/**
 *
 * @author bairen
 *
 */
public class Neptune {

    public static final String VERSION = "neptune-1.0.1";

    /**
     *  创建 NeptuneConfig.NeptuneConfigBuilder
     * @return
     */
    public static NeptuneConfig.NeptuneConfigBuilder config() {
        return NeptuneConfig.builder();
    }

    /**
     * 通过 NeptuneConfig 创建 NeptuneClient
     * @param config
     * @return
     */
    public static NeptuneClient client(NeptuneConfig config) {
        Assert.notNull(config.getHost(), "host must not be null");
        DefaultNeptuneClient client = new DefaultNeptuneClient();
        client.init(config);
        return client;
    }
}
