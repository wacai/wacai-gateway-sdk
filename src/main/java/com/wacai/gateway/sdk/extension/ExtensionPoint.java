package com.wacai.gateway.sdk.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author bairen
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtensionPoint {

	/**
	* 扩展点名
	*/
	String value();

	/**
	* 排序信息，按照升序排序
	*/
	int order() default 0;
}
