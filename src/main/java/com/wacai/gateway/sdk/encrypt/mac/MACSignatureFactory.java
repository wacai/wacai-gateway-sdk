package com.wacai.gateway.sdk.encrypt.mac;

import com.wacai.gateway.sdk.NeptuneConfig;
import com.wacai.gateway.sdk.common.AbstractFactory;
import com.wacai.gateway.sdk.common.Assert;
import com.wacai.gateway.sdk.extension.ExtensionPoint;
import com.wacai.gateway.sdk.encrypt.Signature;
import com.wacai.gateway.sdk.encrypt.SignatureFactory;

/**
 * @author bairen
 */
@ExtensionPoint("mac")
public class MACSignatureFactory extends AbstractFactory<Signature> implements SignatureFactory {

    @Override
    public Signature getSignature(NeptuneConfig config) {
        return super.get(config);
    }

    @Override
    protected Signature create(NeptuneConfig config) {
        MACSignature macSignature = new MACSignature();
        macSignature.setKey(config.getAppSecret());
        return macSignature;
    }

    @Override
    protected String key(NeptuneConfig config) {
        Assert.notNull(config.getAppSecret(), "appSecret is null");
        return config.getAppSecret();
    }

}
