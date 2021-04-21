package com.aqinn.actmanagersys.mobile.model;

import com.aqinn.actmanagersys.mobile.actcard.ActCardType;

import java.util.List;

/**
 * 插入活动的消息 - EventBus专用
 *
 * @author Aqinn
 * @date 2021/4/21 2:47 PM
 */
public class InsertActMessage {

    private ActShow act;

    private ActCardType type;

    private List<AttendShow> attendList;

    public InsertActMessage(ActCardType type, ActShow act, List<AttendShow> attendList) {
        this.act = act;
        this.type = type;
        this.attendList = attendList;
    }

    public ActShow getAct() {
        return act;
    }

    public void setAct(ActShow act) {
        this.act = act;
    }

    public ActCardType getType() {
        return type;
    }

    public void setType(ActCardType type) {
        this.type = type;
    }

    public List<AttendShow> getAttendList() {
        return attendList;
    }

    public void setAttendList(List<AttendShow> attendList) {
        this.attendList = attendList;
    }
}
