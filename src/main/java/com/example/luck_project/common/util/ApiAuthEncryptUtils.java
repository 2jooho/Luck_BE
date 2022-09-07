package com.example.luck_project.common.util;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

public class ApiAuthEncryptUtils {

    private transient final SecretKeySpec sks;
    private transient final IvParameterSpec ips;

    public ApiAuthEncryptUtils(String secretKey) throws UnsupportedEncodingException {
        final String key = secretKey;
        final String iv  = secretKey.substring(0, 16);
        if (StringUtils.isEmpty(key)) {
            throw new IllegalStateException("Property 'scc.auth.encrypt.iv' must not be null");
        }
        if (StringUtils.isEmpty(iv)) {
            throw new IllegalStateException("Property 'scc.auth.encrypt.iv' must not be null");
        }

        // this.sks = new SecretKeySpec(key.getBytes(CharEncoding.UTF_8), "AES");
        // this.ips = new IvParameterSpec(iv.getBytes(CharEncoding.UTF_8));
        this.sks = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        this.ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 문자열을 암호화한다.
     * @param plainText
     * @return
     */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sks, ips);
            byte[] bytes = cipher.doFinal(plainText.getBytes("UTF-8"));
            return bytesToHex(bytes);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        } catch (GeneralSecurityException gse) {
            /*
             * InvalidKeyException
             * NoSuchPaddingException
             * NoSuchAlgorithmException
             * InvalidAlgorithmParameterException
             * BadPaddingException
             * IllegalBlockSizeException
             */

            throw new IllegalStateException(gse);
        }
    }

    /**
     * 암호화된 문자열을 복호화한다.
     * @param encryptedText
     * @return
     */
    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sks, ips);
            byte[] bytes = cipher.doFinal(hexToBytes(encryptedText));
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        } catch (GeneralSecurityException gse) {
            /*
             * InvalidKeyException
             * NoSuchPaddingException
             * NoSuchAlgorithmException
             * InvalidAlgorithmParameterException
             * BadPaddingException
             * IllegalBlockSizeException
             */

            throw new IllegalStateException(gse);
        }
    }

    /**
     * HEX문자열로 바이트배열을 변환한다.
     * @param hex HEX문자열
     * @return
     */
    private byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] result = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }

        return result;
    }

    /**
     * 바이트배열을 HEX문자열로 변환한다.
     * @param bytes 바이트배열
     * @return
     */
    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }


    public static void main(String args[]) {
        /*
         * try { String bfStr = "20220906131900"+"SCO0221"; ApiAuthEncryptUtils e = new
         * ApiAuthEncryptUtils("6b89e077391144bdaad69b03a35957e8");
         * System.out.println(e.encrypt(bfStr)); System.out.println(e.decrypt(
         * "87A4741729AE9DF9AC2D495075D2A97BAB2E8C765CEEFCB56E178FFC3E93D5AE")); } catch
         * (UnsupportedEncodingException e) { e.printStackTrace(); }
         */
    }
}