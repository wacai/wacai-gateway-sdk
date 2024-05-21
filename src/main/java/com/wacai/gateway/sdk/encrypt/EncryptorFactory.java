package com.wacai.gateway.sdk.encrypt;


import com.wacai.gateway.sdk.NeptuneConfig;

/**
 * 
 * EncryptorFactory
 * 
 * @author bairen
 *
 */
public interface EncryptorFactory {

	/**
	 * 
	 * @param config
	 * @return
	 */
	public Encryptor getEncryptor(NeptuneConfig config);
}
