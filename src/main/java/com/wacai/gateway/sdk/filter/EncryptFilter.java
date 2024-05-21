package com.wacai.gateway.sdk.filter;

import com.wacai.gateway.sdk.*;
import com.wacai.gateway.sdk.contract.Headers;
import com.wacai.gateway.sdk.encrypt.EncryptorFactory;
import com.wacai.gateway.sdk.extension.ExtensionLoader;
import com.wacai.gateway.sdk.extension.ExtensionPoint;
import  com.wacai.gateway.sdk.encrypt.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint(value = "encryptFilter", order = -50000)
public class EncryptFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(EncryptFilter.class);

	private static final ExtensionLoader<EncryptorFactory> ENCRYPTOR_FACTORY_LOADER = ExtensionLoader
			.getExtensionLoader(EncryptorFactory.class);

	/**
	 * 加密
	 * 
	 * @param invoker
	 * @param invocation
	 */
	protected void encrypt(Invoker invoker, Invocation invocation) {

		NeptuneConfig config = invoker.getConfig();
		String encryptorName = config.getEncryptor();

		if (encryptorName != null) {

			Encryptor encryptor = ENCRYPTOR_FACTORY_LOADER
					.getExtension(encryptorName)
					.getEncryptor(config);
			invocation.getHeaders().put(Headers.CONTENT_TYPE, "application/octet-stream");
			String encryptedContent = encryptor.encryptBase64(invocation.getData());
			invocation.setData(encryptedContent.getBytes());

			if (config.isDebug()) {
				String info = String.format("encrypt data. appId=%s, api=%s, data=%s", config.getAppKey(),
						invocation.getApiName(),
						encryptedContent);
				LOG.info(info);
			}
		}
	}

	/**
	 * 解密
	 * @param invoker
	 * @param invocation
	 * @param result
	 */
	protected void decrypt(Invoker invoker, Invocation invocation, Result result) {

		NeptuneConfig config = invoker.getConfig();
		String encryptorName = config.getEncryptor();

		if (encryptorName != null && !result.hasException()) {

			Encryptor encryptor = ENCRYPTOR_FACTORY_LOADER
					.getExtension(encryptorName)
					.getEncryptor(config);

			result.setData(encryptor.decryptBase64(result.getData()));

			if (config.isDebug()) {
				String info = String.format("encrypt data. appId=%s, api=%s", config.getAppKey(),
						invocation.getApiName());
				LOG.info(info);
			}
		}
	}

	@Override
	public Result invoke(Invoker invoker, Invocation invocation) {
		encrypt(invoker, invocation);
		Result result = invoker.invoke(invocation);
		decrypt(invoker, invocation, result);
		return result;
	}

}
