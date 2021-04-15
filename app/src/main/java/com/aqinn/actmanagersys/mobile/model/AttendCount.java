package com.aqinn.actmanagersys.mobile.model;

/**
 * 签到人数
 * @author Aqinn
 * @date 2021/4/15 5:23 PM
 */
public class AttendCount {

    private Integer haveAttendCount;

    private Integer shouldAttendCount;

    public AttendCount() {
    }

    public AttendCount(Integer haveAttendCount, Integer shouldAttendCount) {
        this.haveAttendCount = haveAttendCount;
        this.shouldAttendCount = shouldAttendCount;
    }

    public Integer getHaveAttendCount() {
        return haveAttendCount;
    }

    public void setHaveAttendCount(Integer haveAttendCount) {
        this.haveAttendCount = haveAttendCount;
    }

    public Integer getShouldAttendCount() {
        return shouldAttendCount;
    }

    public void setShouldAttendCount(Integer shouldAttendCount) {
        this.shouldAttendCount = shouldAttendCount;
    }

    @Override
    public String toString() {
        return "AttendCount{" +
                "haveAttendCount=" + haveAttendCount +
                ", shouldAttendCount=" + shouldAttendCount +
                '}';
    }

}
