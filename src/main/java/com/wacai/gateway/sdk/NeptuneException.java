package com.wacai.gateway.sdk;

/**
 * Neptune Exception
 * 
 * @author bairen
 *
 */
public class NeptuneException extends RuntimeException {

	private int code;

	private static final long serialVersionUID = 7815426752583648734L;

	public NeptuneException() {
		super();
	}

	public NeptuneException(String message, Throwable cause) {
		super(message, cause);
	}

	public NeptuneException(String message) {
		super(message);
	}

	public NeptuneException(Throwable cause) {
		super(cause);
	}

	public NeptuneException(int code) {
		super();
		this.code = code;
	}

	public NeptuneException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public NeptuneException(int code, String message) {
		super(message+", errorCode="+code);
		this.code = code;
	}

	public NeptuneException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
