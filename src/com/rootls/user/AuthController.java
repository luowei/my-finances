package com.rootls.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午7:24
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/login")
    public String login(){
         return "login";
    }

    @RequestMapping("/logout")
    public String denied(){
        return "login";
    }

    @ResponseBody
    @RequestMapping("/reSetAdmin")
    public String reSetAdmin(){
        userRepository.reSetAdmin();
        return "{\"code\":1,\"msg\":\"reSetAdmin 成功\"}";
    }

    @ResponseBody
    @RequestMapping("/getSecretKey")
    public String getSecretKey(String username){
        String key = userRepository.getSecretKey(username);
        return "{\"code\":1,\"key\":\""+key+"\"}";
    }

}
