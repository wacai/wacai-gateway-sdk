package com.wacai.gateway.sdk.encrypt.des;

import com.wacai.gateway.sdk.encrypt.Encryptor;
import com.wacai.gateway.sdk.common.Constants;
import com.wacai.gateway.sdk.encrypt.AbstractEncryptor;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

/**
 * DES Encryptor
 * 
 * @author bairen
 *
 */
public class DESEncryptor extends AbstractEncryptor implements Encryptor {

	private String algorithm = "DES";

	private String key;

	@Override
	public byte[] encrypt(byte[] content) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			Key secretKey = generateKey(key);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(content);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] decrypt(byte[] content) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			Key secretKey = generateKey(key);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(content);

		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private Key generateKey(String key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes(Constants.DEFAULT_CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		return keyFactory.generateSecret(dks);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
