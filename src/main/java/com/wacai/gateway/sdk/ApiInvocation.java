package com.wacai.gateway.sdk;

import com.wacai.gateway.sdk.common.CollectionUtils;
import lombok.Data;

import java.util.Map;

@Data
public class ApiInvocation implements Invocation {

    private String url;
    private String apiName;
    private String apiVersion;
    private Map<String, String> headers = CollectionUtils.newKeyCaseInsensitiveMap();
    private byte[] data;

    @Override
    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

}
