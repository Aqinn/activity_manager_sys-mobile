package com.aqinn.actmanagersys.mobile.model;

import com.aqinn.actmanagersys.mobile.base.PublicConfig;

import org.litepal.crud.LitePalSupport;

/**
 * 用户类
 *
 * @author Aqinn
 * @date 2021/3/29 1:33 PM
 */
public class User extends LitePalSupport {

    // 账号
    private String account;

    // 密码
    private String pwd;

    // 昵称
    private String name;

    // 联系方式类型
    // 1: 微信
    // 2: 邮箱
    // 3: 电话
    // 其它: 未知
    private int contactType;

    // 联系方式
    private String contact;

    // 性别 0: 女 1: 男 其它: 未知
    private int sex;

    // 简介
    private String intro;

    /**
     * 测试用
     */
    public User() {
    }

    /**
     * 构造一个完整的用户
     */
    public User(String account, String pwd, String name, int contactType, String contact, int sex, String intro) {
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.contactType = contactType;
        this.contact = contact;
        this.sex = sex;
        this.intro = intro;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactType() {
        return contactType;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", contactType=" + contactType +
                ", contact='" + contact + '\'' +
                ", sex=" + sex +
                ", intro='" + intro + '\'' +
                '}';
    }

}
