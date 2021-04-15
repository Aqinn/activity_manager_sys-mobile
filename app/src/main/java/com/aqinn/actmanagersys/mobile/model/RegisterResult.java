package com.aqinn.actmanagersys.mobile.model;

/**
 * 注册成功的返回数据
 * @author Aqinn
 * @date 2021/4/15 4:12 PM
 */
public class RegisterResult {

    private Long id;

    public RegisterResult() {
    }

    public RegisterResult(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "id=" + id +
                '}';
    }

}
