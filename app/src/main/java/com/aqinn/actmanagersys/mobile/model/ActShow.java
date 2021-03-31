package com.aqinn.actmanagersys.mobile.model;

import org.litepal.crud.LitePalSupport;

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

    private String name;

    private String beginTime;

    private String endTime;

    private String location;

    private String intro;

    // 活动状态 1: "未开始" 2: "进行中" 3: "已结束"
    private Integer status;

    public ActShow() {
    }

    // 构建一个完全的活动信息展示类
    public ActShow(Long ownerId, Long actId, Long code, Long pwd, Long creatorId, String creatorAccount, String name, String beginTime, String endTime, String location, String intro, Integer status) {
        this.ownerId = ownerId;
        this.actId = actId;
        this.code = code;
        this.pwd = pwd;
        this.creatorId = creatorId;
        this.creatorAccount = creatorAccount;
        this.name = name;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        this.intro = intro;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
