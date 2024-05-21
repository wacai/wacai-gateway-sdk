package com.wacai.gateway.sdk.mapper;

import java.lang.reflect.Type;

/**
 * 
 * @author bairen
 *
 */
public interface Mapper {

	/**
	 * 
	 * @param source
	 * @param targetType
	 * @return
	 */
	<T> T read(String source, Type targetType);
}
