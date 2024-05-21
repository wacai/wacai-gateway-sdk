package com.wacai.gateway.sdk.filter;

import com.wacai.gateway.sdk.*;
import com.wacai.gateway.sdk.common.StringUtils;
import com.wacai.gateway.sdk.encrypt.Signature;
import com.wacai.gateway.sdk.encrypt.SignatureFactory;
import com.wacai.gateway.sdk.extension.ExtensionLoader;
import com.wacai.gateway.sdk.extension.ExtensionPoint;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import static com.wacai.gateway.sdk.common.Constants.*;
import static com.wacai.gateway.sdk.contract.Headers.*;

/**
 * 
 * @author bairen
 *
 */
@ExtensionPoint(value = "signFilter", order = -10000)
public class SignFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(SignFilter.class);

	@Override
	public Result invoke(Invoker invoker, Invocation invocation) {

		doSign(invoker, invocation);
		Result result = invoker.invoke(invocation);
		doVerify(invoker, invocation, result);
		return result;
	}

	protected void doSign(Invoker invoker, Invocation invocation) {
		NeptuneConfig config = invoker.getConfig();
		String signatureName = config.getSignature();

		if (signatureName != null) {

			Signature signature = ExtensionLoader
					.getExtensionLoader(SignatureFactory.class)
					.getExtension(signatureName)
					.getSignature(config);

			String signPlainText = getSignPlainText(invocation, invocation.getHeaders(), invocation.getData());
			String signatureStr = signature.sign(StringUtils.getByteQuiet(signPlainText, DEFAULT_CHARSET));
			invocation.setHeader(SIGNATURE, signatureStr);

			if (config.isDebug()) {
				String info = String.format("add signature. appId=%s, api=%s, signature=%s", config.getAppKey(),
						invocation.getApiName(),
						signatureStr);
				LOG.info(info);
			}
		}

	}

	protected String getSignPlainText(Invocation invocation,Map<String, String> headers, byte[]data) {
		
		String headerString = TIMESTAMP + EQ + headers.get(TIMESTAMP) + AND + APP_KEY + EQ + headers.get(APP_KEY);
		String bodyMd5 = Base64.encodeBase64String(DigestUtils.md5(data));
		String signPlainText = invocation.getApiName() + SPLIT + invocation.getApiVersion() + SPLIT + headerString
				+ SPLIT + bodyMd5;
		
		return signPlainText;
	}
	
	protected void doVerify(Invoker invoker, Invocation invocation, Result result) {
		NeptuneConfig config = invoker.getConfig();
		String verifierName = config.getVerifier();

		if (verifierName != null) {
			Signature signature = ExtensionLoader
					.getExtensionLoader(SignatureFactory.class)
					.getExtension(verifierName)
					.getSignature(config);

			String signPlainText =  getSignPlainText(invocation, result.getHeaders(), result.getData());
			String signatureStr = signature.sign(StringUtils.getByteQuiet(signPlainText, DEFAULT_CHARSET));
			String signatureFromServer = result.getHeaders().get(SIGNATURE);

			if (!signatureStr.equals(signatureFromServer)) {
				LOG.warn("signature verify failed! local signature:{}, receive signature:{}",signatureStr,signatureFromServer);
				throw new NeptuneException("signature verify failed!");
			}
		}

	}

}
