package com.wacai.gateway.sdk.invoker;

import com.wacai.gateway.sdk.Invoker;
import com.wacai.gateway.sdk.NeptuneConfig;

/**
 * 
 * @author bairen
 *
 */
public abstract class AbstractInvoker implements Invoker {

	private NeptuneConfig config;

	public AbstractInvoker(NeptuneConfig config) {
		super();
		this.config = config;
	}


	@Override
	public NeptuneConfig getConfig() {
		return config;
	}

	

}
