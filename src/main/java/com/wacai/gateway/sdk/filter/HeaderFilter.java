package com.wacai.gateway.sdk.filter;

import com.wacai.gateway.sdk.*;
import com.wacai.gateway.sdk.common.StringUtils;
import com.wacai.gateway.sdk.contract.Headers;
import com.wacai.gateway.sdk.extension.ExtensionPoint;

/**
 *
 * @author bairen
 *
 */
@ExtensionPoint(value = "headerFilter", order = -80000)
public class HeaderFilter implements Filter {

    @Override
    public Result invoke(Invoker invoker, Invocation invocation) {
        long timestamp = System.currentTimeMillis();
        invocation.setHeader(Headers.CONTENT_TYPE, Headers.CONTENT_TYPE_JSON);
        invocation.setHeader(Headers.TIMESTAMP, String.valueOf(timestamp));
        invocation.setHeader(Headers.USER_AGENT, Neptune.VERSION);
        invocation.setHeader(Headers.APP_KEY, invoker.getConfig().getAppKey());
        if (invocation.getData() == null) {
            invocation.setData(StringUtils.EMPTY_STRING.getBytes());
        }
        return invoker.invoke(invocation);
    }

}

