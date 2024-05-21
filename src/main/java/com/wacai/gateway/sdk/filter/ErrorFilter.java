package com.wacai.gateway.sdk.filter;

import com.wacai.gateway.sdk.*;
import com.wacai.gateway.sdk.common.StringUtils;
import com.wacai.gateway.sdk.contract.Headers;
import com.wacai.gateway.sdk.extension.ExtensionPoint;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * handle error code
 * 
 * @author bairen
 *
 */
@ExtensionPoint(value = "errorFilter", order = 0)
public class ErrorFilter implements Filter {

	@Override
	public Result invoke(Invoker invoker, Invocation invocation) {
		Result result = invoker.invoke(invocation);
		String reason = result.getHeaders().get(Headers.ERROR_REASON);
		if (!StringUtils.isEmpty(reason)) {
			String codeStr = result.getHeaders().get(Headers.RESULT_CODE);
			int code = StringUtils.parseInteger(codeStr);
			throw new NeptuneException(code, decode(reason));
		}

		if (result.getCode() != 200) {
		    Integer errorCode = result.getCode();
            String codeStr = result.getHeaders().get(Headers.RESULT_CODE);
            if (StringUtils.isNotEmpty(codeStr)) {
                errorCode = StringUtils.parseInteger(codeStr);
            }
			throw new NeptuneException(errorCode, new String(result.getData()));
		}
		return result;
	}

	protected String decode(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

}
