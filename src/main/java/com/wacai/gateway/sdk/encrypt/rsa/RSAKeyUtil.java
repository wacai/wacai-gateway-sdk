package com.wacai.gateway.sdk.encrypt.rsa;

import com.wacai.gateway.sdk.common.IOUtils;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * Created by eric on 2017/10/18.
 */
public class RSAKeyUtil {

	private final static String RSA = "RSA";

    public static PrivateKey getPrivateKeyOfPKCS8(String privateKey) throws Exception {
        byte[] content = null;
        if (Base64.isBase64(privateKey)) {
            content = Base64.decodeBase64(privateKey);
        }
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(content);
        return keyFactory.generatePrivate(ps);
    }

    public static PublicKey getPublicKeyFromX509(String publicKey) throws Exception {
        byte[] content = null;
        if (Base64.isBase64(publicKey)) {
            content = Base64.decodeBase64(publicKey);
        }
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(content));
    }

    public static PrivateKey getPrivateKeyOfPKCS8(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        byte[] encodedKey = IOUtils.readFully(ins);
        encodedKey = Base64.decodeBase64(encodedKey);
        PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(encodedKey);
        return keyFactory.generatePrivate(ps);
    }

    public static PublicKey getPublicKeyFromX509(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        byte[] encodedKey = IOUtils.readFully(ins);
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey readPrivateKeyFromPEM(
            InputStream inputStream) throws Exception {
        PemObject pemObject;
        try (PemReader pemReader = new PemReader(new InputStreamReader(inputStream, "utf-8"))) {
            pemObject = pemReader.readPemObject();
        }
        byte[] content = pemObject.getContent();
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(content);
        return keyFactory.generatePrivate(ps);
    }


    public static PublicKey readPublicKeyFromPEM(InputStream inputStream) throws Exception {
        PemObject pemObject;
        try (PemReader pemReader = new PemReader(new InputStreamReader(inputStream, "utf-8"))) {
            pemObject = pemReader.readPemObject();
        }
        byte[] content = pemObject.getContent();

        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(content);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }
}
