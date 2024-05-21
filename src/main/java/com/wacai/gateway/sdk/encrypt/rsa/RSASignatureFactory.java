package com.wacai.gateway.sdk.encrypt.rsa;


import com.wacai.gateway.sdk.NeptuneConfig;
import com.wacai.gateway.sdk.common.AbstractFactory;
import com.wacai.gateway.sdk.common.Constants;
import com.wacai.gateway.sdk.common.StringUtils;
import com.wacai.gateway.sdk.encrypt.Signature;
import com.wacai.gateway.sdk.encrypt.SignatureFactory;
import com.wacai.gateway.sdk.extension.ExtensionPoint;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint("rsa")
public class RSASignatureFactory extends AbstractFactory<Signature> implements SignatureFactory {

	@Override
	public Signature getSignature(NeptuneConfig config) {
		return super.get(config);
	}

	@Override
	protected Signature create(NeptuneConfig config) {
		RSASignature signature = new RSASignature();

		String privateKey = config.getPrivateKey();
		String publicKey = config.getPublicKey();

		try {
			if (privateKey != null) {
				signature.setPrivateKey(RSAKeyUtil.getPrivateKeyOfPKCS8(privateKey));
			}
			if (publicKey != null) {
				signature.setPublicKey(RSAKeyUtil.getPublicKeyFromX509(publicKey));
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

		return signature;
	}

	@Override
	protected String key(NeptuneConfig config) {
		String privateKey = config.getPrivateKey();
		String publicKey = config.getPublicKey();
		if (StringUtils.isEmpty(privateKey) &&
				StringUtils.isEmpty(publicKey)) {
			throw new IllegalArgumentException("RSA privateKey and publicKey is null");
		}

		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(privateKey)) {
			sb.append(privateKey);
			sb.append(Constants.SPLIT);
		}

		if (!StringUtils.isEmpty(publicKey)) {
			sb.append(publicKey);
		}
		return sb.toString();
	}

}
