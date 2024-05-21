package com.wacai.gateway.sdk.common;


import com.wacai.gateway.sdk.NeptuneConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author bairen
 *
 * @param <T>
 */
public abstract class AbstractFactory<T> {

	protected Map<String, T> cache = new ConcurrentHashMap<String, T>();

	protected abstract T create(NeptuneConfig config);

	protected abstract String key(NeptuneConfig config);

	protected T get(NeptuneConfig config) {
		String key = key(config);
		T inst = cache.get(key);
		if (inst == null) {
			synchronized (this) {
				inst = cache.get(key);
				if (inst == null) {
					inst = create(config);
					cache.put(key, inst);
				}
			}
		}
		return inst;
	}

}
