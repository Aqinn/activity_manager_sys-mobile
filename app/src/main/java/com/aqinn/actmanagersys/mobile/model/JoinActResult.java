package com.aqinn.actmanagersys.mobile.model;

import java.util.List;

/**
 * 加入活动接口的返回数据
 *
 * @author Aqinn
 * @date 2021/4/21 6:03 PM
 */
public class JoinActResult {

    private ActShow act;

    private List<AttendShow> attendList;

    public JoinActResult(ActShow act, List<AttendShow> attendList) {
        this.act = act;
        this.attendList = attendList;
    }

    public ActShow getAct() {
        return act;
    }

    public void setAct(ActShow act) {
        this.act = act;
    }

    public List<AttendShow> getAttendList() {
        return attendList;
    }

    public void setAttendList(List<AttendShow> attendList) {
        this.attendList = attendList;
    }

}
