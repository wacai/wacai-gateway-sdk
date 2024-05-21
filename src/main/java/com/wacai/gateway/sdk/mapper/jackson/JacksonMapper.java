package com.wacai.gateway.sdk.mapper.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wacai.gateway.sdk.NeptuneException;
import com.wacai.gateway.sdk.extension.ExtensionPoint;
import com.wacai.gateway.sdk.mapper.Mapper;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint("jackson")
public class JacksonMapper implements Mapper {

	private ObjectMapper objMapper = new ObjectMapper();

	public JacksonMapper() {
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public <T> T read(String source, Type targetType) {
		try {
			JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
			return objMapper.readValue(source, javaType);
		} catch (IOException e) {
			throw new NeptuneException(e);
		}
	}

}
