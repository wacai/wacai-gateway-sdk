package com.wacai.gateway.sdk.contract;

/**
 *
 * @author bairen
 *
 */
public enum Signatures {
    RSA("rsa"), MAC("mac");

    private String name;

    private Signatures(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
