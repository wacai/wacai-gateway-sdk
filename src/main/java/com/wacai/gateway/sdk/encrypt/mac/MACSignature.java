package com.wacai.gateway.sdk.encrypt.mac;

import com.wacai.gateway.sdk.common.Constants;
import com.wacai.gateway.sdk.encrypt.Signature;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author bairen
 *
 */
@Data
public class MACSignature implements Signature {

	private String algorithm = "hmacSha256";

	private String key;

	@Override
	public String sign(byte[] content) {

		try {
			Mac mac = Mac.getInstance(algorithm);
			SecretKey secretKey = new SecretKeySpec(key.getBytes(Constants.DEFAULT_CHARSET), algorithm);
			mac.init(secretKey);
			return Base64.encodeBase64URLSafeString(mac.doFinal(content));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public boolean verify(byte[] content, String sign) {
		String str = this.sign(content);
		return str.equals(sign);
	}

}
