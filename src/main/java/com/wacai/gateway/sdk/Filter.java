package com.wacai.gateway.sdk;

/**
 * 
 * @author bairen
 *
 */
public interface Filter {

	/**
	 *
	 * @param invoker
	 * @param invocation
	 * @return
	 */
	public Result invoke(Invoker invoker, Invocation invocation);
}
