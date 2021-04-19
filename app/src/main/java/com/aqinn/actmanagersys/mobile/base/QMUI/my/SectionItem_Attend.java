package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.Objects;

/**
 * 列表二级项 - 签到数据载体
 * TODO 类名需要修改
 *
 * @author Aqinn
 * @date 2021/4/17 5:04 PM
 */
public class SectionItem_Attend implements QMUISection.Model<SectionItem_Attend> {

    private final AttendShow attend;

    public SectionItem_Attend(AttendShow attend) {
        this.attend = attend;
    }

    public AttendShow getAttend() {
        return attend;
    }

    @Override
    public SectionItem_Attend cloneForDiff() {
        return new SectionItem_Attend(getAttend());
    }

    @Override
    public boolean isSameItem(SectionItem_Attend other) {
        // return attend == other.attend || (attend != null && attend.equals(other.attend));
        // 以上语句相当于以下语句
        return Objects.equals(attend, other.attend);
    }

    @Override
    public boolean isSameContent(SectionItem_Attend other) {
        return true;
    }

}