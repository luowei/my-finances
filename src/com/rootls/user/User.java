package com.rootls.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-31
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
public class User extends org.springframework.security.core.userdetails.User implements Serializable {

    Integer id;
    String password;
    String username;
    Collection<GrantedAuthority> authorities;

    String email;
    //数访问令牌
    String token;
    //数据加密密钥
    String secretKey;

    //用户访问角色的权限
    Integer access;
    String role;


    public User(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public User(Integer id, String username, String password, Collection<GrantedAuthority> authorities,
                String email, String token, String secretKey, Integer access, String role) {
        super(username, password, authorities);
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
        this.token = token;
        this.secretKey = secretKey;
        this.access = access;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //======================================================================


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if(this.authorities==null){
            this.authorities= new HashSet<GrantedAuthority>();
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        authorities.add(grantedAuthority);

        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
