package com.wacai.gateway.sdk.contract;

/**
 *
 * @author bairen
 *
 */
public enum Encryptors {
    RSA("rsa"), DES("des");

    private String name;

    private Encryptors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

