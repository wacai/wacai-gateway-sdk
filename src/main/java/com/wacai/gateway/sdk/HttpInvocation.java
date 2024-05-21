package com.wacai.gateway.sdk;

/**
 * represent a HTTP Invocation
 * 
 * @author bairen
 *
 */
public interface HttpInvocation extends Invocation {

	public String getHttpMethod();

	public String getHttpContentType();
	
	public int getTimeoutMs();
}
