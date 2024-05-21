package com.wacai.gateway.sdk;


import com.wacai.gateway.sdk.common.Constants;
import com.wacai.gateway.sdk.encrypt.rsa.RSAMode;
import lombok.Builder;
import lombok.Data;

/**
 * @author bairen
 */
@Data
@Builder
public class NeptuneConfig {

    protected boolean debug;

    protected String host;

    protected String appKey;

    protected String appSecret;

    protected String privateKey;

    protected String publicKey;

    @Builder.Default
    protected int timeoutMs = Constants.DEFAULT_TIMEOUT;

    protected String encryptor;

    @Builder.Default
    protected String signature = Constants.DEFAULT_SIGNATURE;

    protected String verifier;

    @Builder.Default
    protected String invoker = Constants.DEFAULT_INVOKER;

    @Builder.Default
    protected String mapper = Constants.DEFAULT_MAPPER;

    protected RSAMode mode;

}
