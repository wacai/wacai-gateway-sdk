package com.wacai.gateway.sdk.extension;

import com.wacai.gateway.sdk.common.Assert;
import com.wacai.gateway.sdk.common.Constants;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @author bairen
 *
 */
public class ExtensionLoader<T> {

	private static final Object lock = new Object();

	private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<Class<?>, ExtensionLoader<?>>();

	// ==============================

	private final Class<T> type;

	private final ConcurrentMap<String, Object> cachedInstances = new ConcurrentHashMap<String, Object>();

	public ExtensionLoader(Class<T> type) {
		this.type = type;
		this.loadExtensions();
	}

	/**
	 * get ExtensionLoader
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
		if (type == null)
			throw new IllegalArgumentException("Extension type == null");
		if (!type.isInterface()) {
			throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
		}

		ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
		if (loader == null) {
			synchronized (lock) {
				loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
				if (loader == null) {
					loader = new ExtensionLoader<T>(type);
					EXTENSION_LOADERS.put(type, loader);
				}
			}
		}
		return loader;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getExtension(String name) {
		Assert.notNull(name, "Extension name is null");
		Object instance = cachedInstances.get(name);
		Assert.notNull(instance," Extension "+ type.getName()+" instance is null ,name is "+name);
		return (T) instance;
	}

	@SuppressWarnings("unchecked")
	public List<T> getExtensions() {
		List<T> exts = (List<T>) new ArrayList<>(cachedInstances.values());
		Collections.sort(exts, ExtensionPointComparator.COMPARATOR);
		return exts;
	}

	private void loadExtensions() {
		ServiceLoader<T> extensionsLoader = ServiceLoader.load(type);
		Iterator<T> it = extensionsLoader.iterator();
		if (!it.hasNext()) {
			throw new IllegalArgumentException("Extension type(" + type + ") has not ExtensionPoint");
		}
		while (it.hasNext()) {
			T inst = it.next();
			Class<?> type = inst.getClass();
			ExtensionPoint annotation = type.getAnnotation(ExtensionPoint.class);
			if (annotation == null) {
				throw new IllegalArgumentException("Extension type(" + type +
						") is not extension, because WITHOUT @" + ExtensionPoint.class.getSimpleName()
						+ " Annotation!");
			}
			String name = annotation.value();
			if (cachedInstances.containsKey(name)) {
				//override default instance
				if (!inst.getClass().getPackage().getName().startsWith(Constants.PACKAGE_NAME)) {
					cachedInstances.put(name, inst);
				}
			} else {
				cachedInstances.put(name, inst);
			}
		}

	}

}
