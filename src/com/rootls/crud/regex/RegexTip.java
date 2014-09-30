package com.rootls.crud.regex;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * 常用的正则表达式
 * User: luowei
 * Date: 14-4-5
 * Time: 上午3:00
 * To change this template use File | Settings | File Templates.
 */
public class RegexTip implements Serializable {

    @XmlTransient
    Integer id;
    //名称
    String name;
    //详细描棕
    String describe;
    //具体内容，表达示
    String regex;

    public RegexTip() {
    }

    public RegexTip(Integer id, String name, String describe, String regex) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.regex = regex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "RegexTip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", regex='" + regex + '\'' +
                '}';
    }
}
