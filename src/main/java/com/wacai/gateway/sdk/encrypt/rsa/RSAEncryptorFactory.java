package com.wacai.gateway.sdk.encrypt.rsa;

import com.wacai.gateway.sdk.NeptuneConfig;
import com.wacai.gateway.sdk.common.AbstractFactory;
import com.wacai.gateway.sdk.common.Constants;
import com.wacai.gateway.sdk.common.StringUtils;
import com.wacai.gateway.sdk.encrypt.Encryptor;
import com.wacai.gateway.sdk.encrypt.EncryptorFactory;
import com.wacai.gateway.sdk.extension.ExtensionPoint;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint("rsa")
public class RSAEncryptorFactory extends AbstractFactory<Encryptor> implements EncryptorFactory {

	@Override
	public Encryptor getEncryptor(NeptuneConfig config) {
		return super.get(config);
	}

	@Override
	protected Encryptor create(NeptuneConfig config) {
		RSAEncryptor rsaEncryptor = new RSAEncryptor();

		String privateKey = config.getPrivateKey();
		String publicKey = config.getPublicKey();
		try {
			if (privateKey != null) {
				rsaEncryptor.setPrivateKey(RSAKeyUtil.getPrivateKeyOfPKCS8(privateKey));
			}
			if (publicKey != null) {
				rsaEncryptor.setPublicKey(RSAKeyUtil.getPublicKeyFromX509(publicKey));
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		
		rsaEncryptor.setMode(config.getMode());
		return rsaEncryptor;
	}

	@Override
	protected String key(NeptuneConfig config) {
		String privateKey = config.getPrivateKey();
		String publicKey = config.getPublicKey();

		if (StringUtils.isEmpty(publicKey) && StringUtils.isEmpty(privateKey)) {
			throw new IllegalArgumentException("RSA publicKey and privateKey is null");
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
