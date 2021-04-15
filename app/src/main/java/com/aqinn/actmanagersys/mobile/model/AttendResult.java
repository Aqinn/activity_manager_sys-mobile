package com.aqinn.actmanagersys.mobile.model;

/**
 * 视频签到结果
 * @author Aqinn
 * @date 2021/4/15 5:19 PM
 */
public class AttendResult {

    // 签到用户的账号
    private String account;

    // 签到用户的昵称
    private String name;

    private Integer attendType;

    private Long attendTime;

    public AttendResult() {
    }

    public AttendResult(String account, String name, Integer attendType, Long attendTime) {
        this.account = account;
        this.name = name;
        this.attendType = attendType;
        this.attendTime = attendTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    public Long getAttendTime() {
        return attendTime;
    }

    public void setAttendTime(Long attendTime) {
        this.attendTime = attendTime;
    }

    @Override
    public String toString() {
        return "AttendResult{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", attendType=" + attendType +
                ", attendTime=" + attendTime +
                '}';
    }

}
