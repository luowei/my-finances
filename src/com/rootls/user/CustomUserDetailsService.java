package com.rootls.user;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午7:08
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CustomUserDetailsService  implements UserDetailsService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = userRepository.findByUsername(username);
        return user;
    }

}