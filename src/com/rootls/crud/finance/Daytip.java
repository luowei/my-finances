package com.rootls.crud.finance;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-14
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class Daytip implements Serializable{

    Integer id;

    String tipDateStr;
    Date tipDate;

    String week;
    Float money;
    String desc;
    String type;

    String startDate;
    String endDate;

    public Daytip() {
    }

    public Daytip(String tipDateStr, Date tipDate, String week, Float money, String desc) {
        this.tipDateStr = tipDateStr;
        this.tipDate = tipDate;
        this.week = week;
        this.money = money;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipDateStr() {
        return tipDateStr;
    }

    public void setTipDateStr(String tipDateStr) {
        this.tipDateStr = tipDateStr;
    }

    public Date getTipDate() {
        return tipDate;
    }

    public void setTipDate(Date tipDate) {
        this.tipDate = tipDate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate != null ? startDate.trim() : startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate != null ? endDate.trim() : endDate;
    }
}
