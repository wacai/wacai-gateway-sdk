package com.wacai.gateway.sdk;


import com.wacai.gateway.sdk.common.TypeReference;

/**
 * @author bairen
 */
public interface NeptuneClient {

    /**
     * invoke gateway
     *
     * @param request - the ApiRequest to be used to send data when invoke
     * @return - an ApiResponse from gateway
     * @throws NeptuneException
     */
    ApiResponse invoke(ApiRequest request) throws NeptuneException;

    /**
     * invoke gateway (return generic type)
     * Such as: "neptuneClient.invoke(request,User.class)"
     *
     * @param <T>     - generic type
     * @param request - the ApiRequest to be used to send data when invoke
     * @param clazz   - the return class type
     * @return - a special object response from gateway
     * @throws NeptuneException
     */
    <T> T invoke(ApiRequest request, Class<T> clazz) throws NeptuneException;

    /**
     * invoke gateway (return generic type)
     * <p>
     * Such as: "neptuneClient.invoke(request,TypeReference<List<User>> typeReference)"
     *
     * @param <T>           - generic type
     * @param request       - the ApiRequest to be used to send data when invoke
     * @param typeReference 处理泛型类型丢失问题，如List<Item>
     * @throws NeptuneException
     */
    <T> T invoke(ApiRequest request,
                 TypeReference<T> typeReference) throws NeptuneException;


    /**
     * shutdown
     */
    void shutdown();
}
