package com.wacai.gateway.sdk;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author bairen
 *
 */
public class Result {

	private Throwable exception;
	private Map<String, String> headers = new HashMap<String, String>();
	private int code;
	private byte[] data;

	public Result() {
	}

	public Result(Throwable exception) {
		this.exception = exception;
	}

	public int getCode() {
		return code;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public byte[] getData() {
		return data;
	}

	public boolean hasException() {
		return exception != null;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
