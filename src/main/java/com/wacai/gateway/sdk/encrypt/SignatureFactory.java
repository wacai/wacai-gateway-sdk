package com.wacai.gateway.sdk.encrypt;


import com.wacai.gateway.sdk.NeptuneConfig;

/**
 * 
 * @author bairen
 *
 */
public interface SignatureFactory {

	Signature getSignature(NeptuneConfig config);
}
