package hello.login.web.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptUtil {

    private final static String KEY = "gbajsdpstltm";   // 휴먼엔시스 영문
    private final static String ALGORITHM = "PBEWithMD5AndDES";
    private final static String BASE64 = "base64";

    // 암호화
    public static String encrypt(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setPassword(KEY);    // 암호화할 때 사용하는 키
        pbeEnc.setAlgorithm(ALGORITHM);     // 암호화 알고리즘
        pbeEnc.setStringOutputType(BASE64);     // 인코딩 방식

        return pbeEnc.encrypt(value);
    }

    // 복호화
    public static String decrypt(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setPassword(KEY);    // 암호화할 때 사용하는 키
        pbeEnc.setAlgorithm(ALGORITHM);     // 암호화 알고리즘
        pbeEnc.setStringOutputType(BASE64);     // 인코딩 방식

        return pbeEnc.decrypt(value);
    }
}
