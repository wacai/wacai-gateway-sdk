package com.wacai.gateway.sdk;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ApiRequest {

    private String apiName;
    private String apiVersion;
    private Map<String, String> headers;
    private byte[] data;

    public ApiRequest(String apiName, String apiVersion) {
        this.apiName = apiName;
        this.apiVersion = apiVersion;
        this.headers = new HashMap<>();
    }

    public ApiRequest setData(byte[] data) {
        this.data = data;
        return this;
    }

    public ApiRequest setData(String data) {
        this.data = data.getBytes();
        return this;
    }

    public ApiRequest putHeader(String key, long val) {
        this.headers.put(key, String.valueOf(val));
        return this;
    }

    public ApiRequest putHeader(String key, int val) {
        this.headers.put(key, String.valueOf(val));
        return this;
    }

    public ApiRequest putHeader(String key, String val) {
        this.headers.put(key, val);
        return this;
    }

    public ApiRequest putHeaders(Map<? extends String, ? extends String> entities) {
        this.headers.putAll(entities);
        return this;
    }

}
