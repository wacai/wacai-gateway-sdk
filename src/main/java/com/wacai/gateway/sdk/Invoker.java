package com.wacai.gateway.sdk;

/**
 * 
 * @author bairen
 *
 */
public interface Invoker {

	/**
	 * get config
	 * @return
	 */
	public NeptuneConfig getConfig();

	/**
	 * 
	 * @param invocation
	 * @return
	 */
	public Result invoke(Invocation invocation) throws NeptuneException;

	/**
	 * subclass can override it
	 */
	public default void close() {
	}
}
