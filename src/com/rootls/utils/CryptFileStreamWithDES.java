package com.rootls.utils;
/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午8:11
 * To change this template use File | Settings | File Templates.
 */

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class CryptFileStreamWithDES {
    private static Cipher ecipher;
    private static Cipher dcipher;
    // 8-byte initialization vector
    private static byte[] iv = {
            (byte) 0xB2, (byte) 0x12, (byte) 0xD5, (byte) 0xB2,
            (byte) 0x44, (byte) 0x21, (byte) 0xC3, (byte) 0xC3
    };

    static {
        try {
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println("Invalid Alogorithm Parameter:" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm:" + e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.out.println("No Such Padding:" + e.getMessage());
        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            encrypt(new FileInputStream("cleartext.txt"), new FileOutputStream("encrypted.dat"));
            decrypt(new FileInputStream("encrypted.dat"), new FileOutputStream("cleartext-reversed.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found:" + e.getMessage());
        }
    }

    public static void encrypt(InputStream is, OutputStream os) {
        try {
            byte[] buf = new byte[1024];
            // bytes at this stream are first encoded
            os = new CipherOutputStream(os, ecipher);
            // read in the clear text and write to out to encrypt
            int numRead = 0;
            while ((numRead = is.read(buf)) >= 0) {
                os.write(buf, 0, numRead);
            }
            // close all streams
            os.close();
        } catch (IOException e) {
            System.out.println("I/O Error:" + e.getMessage());
        }
    }

    public static void decrypt(InputStream is, OutputStream os) {
        try {
            byte[] buf = new byte[1024];
            // bytes read from stream will be decrypted
            CipherInputStream cis = new CipherInputStream(is, dcipher);
            // read in the decrypted bytes and write the clear text to out
            int numRead = 0;
            while ((numRead = cis.read(buf)) >= 0) {
                os.write(buf, 0, numRead);
            }
            // close all streams
            cis.close();
            is.close();
            os.close();
        } catch (IOException e) {
            System.out.println("I/O Error:" + e.getMessage());
        }
    }
}
