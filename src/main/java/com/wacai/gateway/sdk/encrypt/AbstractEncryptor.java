package com.wacai.gateway.sdk.encrypt;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author bairen
 *
 */
public abstract class AbstractEncryptor implements Encryptor {

	@Override
	public String encryptBase64(byte[] content) {
		return Base64.encodeBase64URLSafeString(encrypt(content));
	}

	@Override
	public byte[] decryptBase64(byte[] content) {
		return this.decrypt(Base64.decodeBase64(content));
	}

}
