package com.zane.test_ums.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * @author Zanezeng
 */
@Component
public class AesCipherUtil {
    /**
     * AES加密私钥
     */
    private static final String AES_ENCRYPT_KEY = "U0JBUElKV1RkNA==";
    private static final String ENCODING = "UTF-8";
    private static final String FILL_VECTOR = "1234560405060708";

    /**
     * aes加密
     * @param plainText：待加密的明文(email + password)
     * @return 密文
     */
    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = AES_ENCRYPT_KEY.getBytes();
            SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(FILL_VECTOR.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(ENCODING));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * aes解密
     * @param cipherText：待解密的密文
     * @return 明文(email + password)
     */
    public static String decrypt(String cipherText) {
        try {
            byte[] raw = AES_ENCRYPT_KEY.getBytes("ASCII");
            SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(FILL_VECTOR.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);

            byte[] encrypted = Base64.decodeBase64(cipherText);
            byte[] original = cipher.doFinal(encrypted);

            return new String(original, ENCODING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
