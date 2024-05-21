package com.wacai.gateway.sdk.encrypt.rsa;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * 
 * @author bairen
 *
 */
@Data
public class RSASignature implements com.wacai.gateway.sdk.encrypt.Signature {

	private PrivateKey privateKey;

	private PublicKey publicKey;

	private String algorithm = "SHA1WithRSA";

	@Override
	public String sign(byte[] content) {

		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(privateKey);
			signature.update(content);
			return Base64.encodeBase64URLSafeString(signature.sign());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public boolean verify(byte[] content, String sign) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(publicKey);
			signature.update(content);
			return signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

	}

}
