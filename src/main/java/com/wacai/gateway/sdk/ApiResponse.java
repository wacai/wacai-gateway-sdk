package com.wacai.gateway.sdk;

import java.util.Map;

/**
 * 注：header不区分大小写
 *
 * @author bairen
 *
 */
public class ApiResponse {

    private Map<String, String> headers;
    private int code;
    private byte[] data;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}