package com.aqinn.actmanagersys.mobile.actcard;

import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.Objects;

/**
 * 列表一级项 - 活动数据载体
 * TODO 类名需要修改
 *
 * @author Aqinn
 * @date 2021/4/17 5:04 PM
 */
public class SectionHeader_Act implements QMUISection.Model<SectionHeader_Act> {

    private final ActShow act;

    public SectionHeader_Act(ActShow act) {
        this.act = act;
    }

    public ActShow getAct() {
        return act;
    }

    @Override
    public SectionHeader_Act cloneForDiff() {
        return new SectionHeader_Act(getAct());
    }

    @Override
    public boolean isSameItem(SectionHeader_Act other) {
        // return act == other.act || (act != null && act.equals(other.act));
        // 以上语句相当于以下语句
        return Objects.equals(act, other.act);
    }

    @Override
    public boolean isSameContent(SectionHeader_Act other) {
        return true;
    }

}
