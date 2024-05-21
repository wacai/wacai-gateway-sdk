package com.wacai.gateway.sdk.encrypt;

/**
 * Encryptor 
 * 
 * @author bairen
 *
 */
public interface Encryptor {

	/**
	 * encrypt.
	 * 
	 * @param content
	 * @return
	 */
	byte[] encrypt(byte[] content);

	/**
	 * decrypt.
	 * @param content
	 * @return
	 */
	byte[] decrypt(byte[] content);

	/**
	 * 加密. 按照base64输出
	 * @param content
	 * @return
	 */
	String encryptBase64(byte[] content);

	/**
	 * 解密，输入是Base64
	 * @param content
	 * @return
	 */
	byte[] decryptBase64(byte[] content);
}
