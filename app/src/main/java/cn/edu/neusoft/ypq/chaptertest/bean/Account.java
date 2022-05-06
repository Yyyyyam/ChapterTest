package cn.edu.neusoft.ypq.chaptertest.bean;

import java.io.Serializable;

/**
 * 作者:颜培琦
 * 时间:2022/5/5
 * 功能:Account
 */
public class Account implements Serializable {
    private Integer aid;
    private String name;
    private Double money;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "aid=" + aid +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
