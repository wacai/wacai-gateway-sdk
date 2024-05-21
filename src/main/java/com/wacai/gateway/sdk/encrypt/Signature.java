package com.wacai.gateway.sdk.encrypt;

/**
 * Signature
 *
 * @author bairen
 */
public interface Signature {

    /**
     * generate a sign of content
     *
     * @param content
     * @return the sign of content
     */
    String sign(byte[] content);

    /**
     * verify the sign and the content
     *
     * @param content- a content to be verified
     * @param sign-    a sign to be verified
     * @return: true if verify pass otherwise return false
     */
    boolean verify(byte[] content, String sign);
}
