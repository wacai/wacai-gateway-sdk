package com.wacai.gateway.sdk.common;

import com.wacai.gateway.sdk.contract.Encryptors;
import com.wacai.gateway.sdk.contract.Signatures;

import java.io.File;

/**
 *
 * @author bairen
 *
 */
public class Constants {

    public static final String PACKAGE_NAME = "com.wacai.gateway";

    public static final File USER_HOME = new File(System.getProperty("user.home"));

    public static final char AND = '&';

    public static final char EQ = '=';

    public static final char SPLIT = '|';

    public static final String DEFAULT_CHARSET = "utf-8";

    public static final String DEFAULT_ENCRYPTOR = Encryptors.DES.getName();

    public static final String DEFAULT_SIGNATURE = Signatures.MAC.getName();

    public static final int DEFAULT_TIMEOUT = 5000;

    public static final String DEFAULT_INVOKER = "okhttp";

    public static final String DEFAULT_MAPPER = "jackson";


}
