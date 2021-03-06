package com.aqinn.actmanagersys.mobile.model;

import org.litepal.crud.LitePalSupport;

import java.util.Random;

/**
 * 活动信息展示类 - 主要用于数据展示以及数据缓存
 *
 * @author Aqinn
 * @date 2021/3/31 4:36 PM
 */
public class ActShow extends LitePalSupport {

    // 数据被谁持有
    private Long ownerId;

    private Long actId;

    private Long code;

    private Long pwd;

    private Long creatorId;

    private String creatorAccount;

    private String creatorName;

    private String name;

    private String startTime;

    private String endTime;

    private String location;

    private String desc;

    // 活动状态 1: "未开始" 2: "进行中" 3: "已结束"
    private Integer status;

    public ActShow() {
        this.ownerId = 0L;
        this.actId = 0L;
        this.code = 123456L;
        this.pwd = 123456L;
        this.creatorId = 0L;
        this.creatorAccount = "creatorAccount";
        this.creatorName = "creatorName";
        this.name = "测试活动名称";
        this.startTime = "2021-4-18 21:55";
        this.endTime = "2021-4-18 23:59";
        this.location = "木铎楼B504";
        this.desc = "我是一段很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的简介";
        this.status = 1;
    }

    public ActShow(String name) {
        this.name = name;
        this.creatorName = name;
        this.startTime = name;
        this.endTime = name;
        this.location = name;
        this.creatorAccount = name;
        this.desc = name;
        this.status = new Random().nextInt(3);
    }

    // 构建一个完全的活动信息展示类
    public ActShow(Long ownerId, Long actId, Long code, Long pwd, Long creatorId, String creatorAccount, String creatorName, String name, String startTime, String endTime, String location, String desc, Integer status) {
        this.ownerId = ownerId;
        this.actId = actId;
        this.code = code;
        this.pwd = pwd;
        this.creatorId = creatorId;
        this.creatorAccount = creatorAccount;
        this.creatorName = creatorName;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.desc = desc;
        this.status = status;
    }

    public void copyOther(ActShow other) {
        this.ownerId = other.getOwnerId();
        this.actId = other.getActId();
        this.code = other.getCode();
        this.pwd = other.getPwd();
        this.creatorId = other.getCreatorId();
        this.creatorAccount = other.getCreatorAccount();
        this.creatorName = other.getCreatorName();
        this.name = other.getName();
        this.startTime = other.getStartTime();
        this.endTime = other.getEndTime();
        this.location = other.getLocation();
        this.desc = other.getDesc();
        this.status = other.getStatus();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getPwd() {
        return pwd;
    }

    public void setPwd(Long pwd) {
        this.pwd = pwd;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorAccount() {
        return creatorAccount;
    }

    public void setCreatorAccount(String creatorAccount) {
        this.creatorAccount = creatorAccount;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActShow actShow = (ActShow) o;

        if (ownerId != null ? !ownerId.equals(actShow.ownerId) : actShow.ownerId != null)
            return false;
        if (actId != null ? !actId.equals(actShow.actId) : actShow.actId != null) return false;
        if (code != null ? !code.equals(actShow.code) : actShow.code != null) return false;
        if (pwd != null ? !pwd.equals(actShow.pwd) : actShow.pwd != null) return false;
        if (creatorId != null ? !creatorId.equals(actShow.creatorId) : actShow.creatorId != null)
            return false;
        if (creatorAccount != null ? !creatorAccount.equals(actShow.creatorAccount) : actShow.creatorAccount != null)
            return false;
        if (creatorName != null ? !creatorName.equals(actShow.creatorName) : actShow.creatorName != null)
            return false;
        if (name != null ? !name.equals(actShow.name) : actShow.name != null) return false;
        if (startTime != null ? !startTime.equals(actShow.startTime) : actShow.startTime != null)
            return false;
        if (endTime != null ? !endTime.equals(actShow.endTime) : actShow.endTime != null)
            return false;
        if (location != null ? !location.equals(actShow.location) : actShow.location != null)
            return false;
        if (desc != null ? !desc.equals(actShow.desc) : actShow.desc != null) return false;
        return status != null ? status.equals(actShow.status) : actShow.status == null;
    }

    @Override
    public int hashCode() {
        int result = ownerId != null ? ownerId.hashCode() : 0;
        result = 31 * result + (actId != null ? actId.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (creatorAccount != null ? creatorAccount.hashCode() : 0);
        result = 31 * result + (creatorName != null ? creatorName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActShow{" +
                "ownerId=" + ownerId +
                ", actId=" + actId +
                ", code=" + code +
                ", pwd=" + pwd +
                ", creatorId=" + creatorId +
                ", creatorAccount='" + creatorAccount + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", location='" + location + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                '}';
    }
}
