package com.rootls.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

import static com.rootls.utils.BASE64.decrypt;
import static com.rootls.utils.BASE64.encrypt;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return encrypt(rawPassword.toString());
//        return (new BASE64Encoder()).encode(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(decrypt(encodedPassword));
//        try {
//            String decode = new String((new BASE64Decoder()).decodeBuffer(encodedPassword));
//            return rawPassword.equals(decode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }
}
