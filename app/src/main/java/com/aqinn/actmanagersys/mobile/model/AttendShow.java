package com.aqinn.actmanagersys.mobile.model;

import org.litepal.crud.LitePalSupport;

/**
 * 签到信息展示类 - 主要用于数据展示以及数据缓存
 *
 * @author Aqinn
 * @date 2021/4/5 12:08 PM
 */
public class AttendShow extends LitePalSupport {

    // 数据被谁持有
    private Long ownerId;

    private Long attendId;

    private Long actId;

    // 活动名称
    private String name;

    private String startTime;

    private String endTime;

    // 用二进制形式表示签到形式，第一位是1为自助签到，第二位是1为视频签到
    // 可以采用ParseUtil中的工具完成解析
    private Integer attendType;

    // 应该签到的人数
    private Integer shouldAttendCount;

    // 已经签到的人数
    private Integer haveAttendCount;

    // 当前的签到状态 1: 未开始 2: 进行中 3: 已结束
    private Integer status;

    // 是否已签到 1: 已签到 2: 未签到
    private Integer uStatus;

    // 仅测试用
    public AttendShow() {
        this.ownerId = 0L;
        this.attendId = 0L;
        this.actId = 0L;
        this.name = "name";
        this.startTime = "2021-4-18 21:55";
        this.endTime = "23:59";
        this.attendType = 1;
        this.shouldAttendCount = 1;
        this.haveAttendCount = 1;
        this.status = 1;
        this.uStatus = 1;
    }

    // 仅测试用
    public AttendShow(String name, String startTime, String endTime, Integer status, Integer shouldAttendCount, Integer haveAttendCount, Integer attendType) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shouldAttendCount = shouldAttendCount;
        this.haveAttendCount = haveAttendCount;
        this.status = status;
        this.attendType = attendType;
    }

    public AttendShow(Long ownerId, Long attendId, Long actId, String name, String startTime, String endTime, Integer attendType, Integer shouldAttendCount, Integer haveAttendCount, Integer status) {
        this.ownerId = ownerId;
        this.attendId = attendId;
        this.actId = actId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendType = attendType;
        this.shouldAttendCount = shouldAttendCount;
        this.haveAttendCount = haveAttendCount;
        this.status = status;
    }

    public AttendShow(Long ownerId, Long attendId, Long actId, String name, String startTime, String endTime, Integer attendType, Integer shouldAttendCount, Integer haveAttendCount, Integer status, Integer uStatus) {
        this.ownerId = ownerId;
        this.attendId = attendId;
        this.actId = actId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendType = attendType;
        this.shouldAttendCount = shouldAttendCount;
        this.haveAttendCount = haveAttendCount;
        this.status = status;
        this.uStatus = uStatus;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
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

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    public Integer getShouldAttendCount() {
        return shouldAttendCount;
    }

    public void setShouldAttendCount(Integer shouldAttendCount) {
        this.shouldAttendCount = shouldAttendCount;
    }

    public Integer getHaveAttendCount() {
        return haveAttendCount;
    }

    public void setHaveAttendCount(Integer haveAttendCount) {
        this.haveAttendCount = haveAttendCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getuStatus() {
        return uStatus;
    }

    public void setuStatus(Integer uStatus) {
        this.uStatus = uStatus;
    }
}
