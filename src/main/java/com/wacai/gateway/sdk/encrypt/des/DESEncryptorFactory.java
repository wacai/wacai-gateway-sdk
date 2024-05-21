package com.wacai.gateway.sdk.encrypt.des;


import com.wacai.gateway.sdk.NeptuneConfig;
import com.wacai.gateway.sdk.common.AbstractFactory;
import com.wacai.gateway.sdk.common.Assert;
import com.wacai.gateway.sdk.encrypt.Encryptor;
import com.wacai.gateway.sdk.encrypt.EncryptorFactory;
import com.wacai.gateway.sdk.extension.ExtensionPoint;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint("des")
public class DESEncryptorFactory extends AbstractFactory<Encryptor> implements EncryptorFactory {

	@Override
	public Encryptor getEncryptor(NeptuneConfig config) {
		return super.get(config);
	}

	@Override
	protected Encryptor create(NeptuneConfig config) {
		DESEncryptor desEncryptor = new DESEncryptor();
		desEncryptor.setKey(config.getAppSecret());
		return desEncryptor;
	}

	@Override
	protected String key(NeptuneConfig config) {
		Assert.notNull(config.getAppSecret(), "appSecret is null");
		return config.getAppSecret();
	}

}
