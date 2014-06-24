package com.rootls.test;

import com.rootls.utils.BASE64;
import com.rootls.utils.JiamiJiemi;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

import static com.rootls.common.Config.secret_key;
import static com.rootls.utils.JiamiJiemi.jiemi;

public class passwordTest {

//    public static String encryp(String pass) throws Exception {
//        byte[] key = "luoweiwe".getBytes();
//        byte[] dataToSend = pass.getBytes();
//
//        Cipher c = Cipher.getInstance("AES");
//        SecretKeySpec k = new SecretKeySpec(key, "AES");
//        c.init(Cipher.ENCRYPT_MODE, k);
//        byte[] encryptedData = c.doFinal(dataToSend);
//        return new String(encryptedData, "UTF-8");
//    }
//
//    public static String decryp(String cryptePass) throws Exception {
//        byte[] key = "luoweiwe".getBytes();
//        byte[] encryptedData = cryptePass.getBytes();
//
//        Cipher c = Cipher.getInstance("AES");
//        SecretKeySpec k = new SecretKeySpec(key, "AES");
//        c.init(Cipher.DECRYPT_MODE, k);
//        byte[] data = c.doFinal(encryptedData);
//        return new String(data, "UTF-8");
//    }
//
//    public static void main(String[] args) throws Exception {
//        String out = encryp("luowei");
//        System.out.println(out);
//
//        String in = decryp(out);
//        System.out.println(in);
//    }

//
    static String algorithm = "DESede";
    static Key key;
    static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance(algorithm);
            key = KeyGenerator.getInstance(algorithm).generateKey();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void main(String[] args) throws Exception {
//        byte[] encryptionBytes = encrypt("luow3423423412341;';'ei");
//        String cryptedPass =  new String(encryptionBytes);
//
//        System.out.println(cryptedPass);
//        System.out.println("Recovered: " + decrypt(cryptedPass.getBytes()));
        String jia = JiamiJiemi.jiami("luowei", secret_key);
        String encod = BASE64.encrypt(jia);
        System.out.print(encod);
        System.out.println(BASE64.decrypt("OTIzOTMwOTE0bWhsY3o="));
        System.out.println(jiemi(BASE64.decrypt(encod),secret_key));
    }

    private static byte[] encrypt(String input) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = input.getBytes();
        return cipher.doFinal(inputBytes);
    }

    private static String decrypt(byte[] encryptionBytes) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
        String recovered = new String(recoveredBytes);
        return recovered;
    }

}