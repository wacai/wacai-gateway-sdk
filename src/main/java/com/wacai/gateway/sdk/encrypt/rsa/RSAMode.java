package com.wacai.gateway.sdk.encrypt.rsa;

/**
 * use bit represent Encrypt/Decrypt key mode
 * 
 * <code>
 * 0: publicKey
 * 1: privateKey
 * </code>
 * 
 * @author bairen
 *
 */
public class RSAMode {

	public static final int ENCRYPT_MARK = 1 << 0;

	public static final int DECRYPT_MARK = 2 << 0;

	private int code;

	public static RSAMode getClientMode() {
		RSAMode mode = new RSAMode();
		mode.encryptByPublic();
		mode.decryptByPublic();
		return mode;
	}

	public static RSAMode getServerMode() {
		RSAMode mode = new RSAMode();
		mode.encryptByPrivate();
		mode.decryptByPrivate();
		return mode;
	}

	public void encryptByPrivate() {
		code |= ENCRYPT_MARK;
	}

	public void encryptByPublic() {
		code &= ~ENCRYPT_MARK;
	}

	public void decryptByPrivate() {
		code |= DECRYPT_MARK;
	}

	public void decryptByPublic() {
		code &= ~DECRYPT_MARK;
	}

	/**
	 * 0: publicKey 1: privateKey
	 * 
	 * @return EncryptMode
	 */
	public int getEncryptMode() {
		return ((code & ENCRYPT_MARK) == ENCRYPT_MARK) ? 1 : 0;
	}

	/**
	 * 0: publicKey 1: privateKey
	 * 
	 * @return DecryptMode
	 */
	public int getDecryptMode() {
		return ((code & DECRYPT_MARK) == DECRYPT_MARK) ? 1 : 0;
	}

}
