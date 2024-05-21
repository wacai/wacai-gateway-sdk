package com.wacai.gateway.sdk;

import java.util.Map;

/**
 * @author bairen
 */
public interface Invocation {

    /**
     * get url.
     *
     * @return return url
     */
    String getUrl();

    /**
     * get api name.
     *
     * @return return api name
     */
    String getApiName();

    /**
     * get api version.
     *
     * @return return api version
     */
    String getApiVersion();

    /**
     * get headers.
     *
     * @return return headers
     */
    Map<String, String> getHeaders();

    /**
     * get data.
     *
     * @return return data
     */
    byte[] getData();

    /**
     * set header
     *
     * @param name  - The name of header to be set
     * @param value - The value of header to be set
     */
    void setHeader(String name, String value);


    /**
     * set data
     *
     * @param data - The data to be set
     */
    void setData(byte[] data);


}
