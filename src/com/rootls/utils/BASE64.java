package com.rootls.utils;
/**
 * BASE64加密解密
 * User: luowei
 * Date: 14-4-1
 * Time: 下午6:41
 * To change this template use File | Settings | File Templates.
 */

import com.rootls.common.Config;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.rootls.common.Config.encoding;

public class BASE64 {

    public static void main(String[] args) {
        String encrypted = encrypt(JiamiJiemi.jiami("rootls.com123","luowei"));
        System.out.println("encrypted:"+encrypted);
        String decrypted = decrypt("MjkzOTMwOTEyMTBsbmItcmtzbjFucQ==");
        System.out.println("Decrypted: " + JiamiJiemi.jiemi(decrypted,"luowei"));
    }

    public static String encrypt(String key) {
        return (new BASE64Encoder()).encodeBuffer(key.getBytes());
    }

    public static String decrypt(String key) {
        try {
            return new String((new BASE64Decoder()).decodeBuffer(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
