package com.aqinn.actmanagersys.mobile.model;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;

/**
 * 登录成功的返回数据
 * @author Aqinn
 * @date 2021/4/15 4:12 PM
 */
public class LoginResult {

    private Long id;

    private String name;

    private String token;

    public LoginResult() {
    }

    public LoginResult(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}
