package com.wacai.gateway.sdk;

/**
 * 
 * @author bairen
 *
 */
public interface InvokerFactory {

	/**
	 * 
	 * @param config
	 * @return
	 */
	public Invoker getInvoker(NeptuneConfig config);
}
