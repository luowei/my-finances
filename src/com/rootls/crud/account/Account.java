package com.rootls.crud.account;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-31
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
public class Account implements Serializable {
    Integer id;
    String siteName;
    String keyName;
    String keySecret;

    public Account() {
    }

    public Account(Integer id, String siteName, String keyName, String keySecret) {
        this.id = id;
        this.siteName = siteName != null ? siteName.trim() : siteName;
        this.keyName = keyName != null ? keyName.trim() : keyName;
        this.keySecret = keySecret != null ? keySecret.trim() : keySecret;;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName != null ? siteName.trim() : siteName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName != null ? keyName.trim() : keyName;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret =  keySecret != null ? keySecret.trim() : keySecret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
