package com.wacai.gateway.sdk.encrypt.rsa;



import com.wacai.gateway.sdk.common.Assert;
import com.wacai.gateway.sdk.encrypt.AbstractEncryptor;
import com.wacai.gateway.sdk.encrypt.Encryptor;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;

/**
 * 
 * @author bairen
 *
 */
public class RSAEncryptor extends AbstractEncryptor implements Encryptor {

	private static final String RSA = "RSA";

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private RSAMode mode;

	RSAEncryptor() {
	}

	@Override
	public byte[] encrypt(byte[] content) {
		Assert.notNull(mode, "mode must not null");
		try {
			Key key = (mode.getEncryptMode() == 1) ? privateKey : publicKey;
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, key);

			int keyLength = ((RSAKey) key).getModulus().bitLength() / 8;
			int encryptLength = keyLength - 11;
			return convert(cipher, content, encryptLength);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

	}

	@Override
	public byte[] decrypt(byte[] content) {
		Assert.notNull(mode, "mode must not null");
		try {
			Key key = (mode.getDecryptMode() == 1) ? privateKey : publicKey;
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, key);

			// 模长(加密数据长度 <= 模长-11)
			int keyLength = ((RSAKey) key).getModulus().bitLength() / 8;

			return convert(cipher, content, keyLength);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

	}

	private Cipher getCipher() throws Exception {
		return Cipher.getInstance(RSA);
	}

	private byte[] convert(Cipher cipher, byte[] content, int keyLength) throws Exception {
		final int inputLen = content.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache = null;
		int i = 0;

		// 对数据分段加密/解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > keyLength) {
				cache = cipher.doFinal(content, offSet, keyLength);
			} else {
				cache = cipher.doFinal(content, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * keyLength;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();

		return decryptedData;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public RSAMode getMode() {
		return mode;
	}

	public void setMode(RSAMode mode) {
		this.mode = mode;
	}

}
