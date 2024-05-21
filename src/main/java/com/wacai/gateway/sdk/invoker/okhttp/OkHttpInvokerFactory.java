package com.wacai.gateway.sdk.invoker.okhttp;


import com.wacai.gateway.sdk.Invoker;
import com.wacai.gateway.sdk.InvokerFactory;
import com.wacai.gateway.sdk.NeptuneConfig;
import com.wacai.gateway.sdk.extension.ExtensionPoint;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint("okhttp")
public class OkHttpInvokerFactory implements InvokerFactory {

	@Override
	public Invoker getInvoker(NeptuneConfig config) {
		return new OkHttpInvoker(config);
	}

}
