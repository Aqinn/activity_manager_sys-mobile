package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * 活动卡片列表 - Model
 *
 * @author Aqinn
 * @date 2021/4/18 4:33 PM
 */
public class ActCardModel implements IActCard.Model {

    public List<ActShow> actList;
    public Map<ActShow, List<AttendShow>> actAttendMap;
    public List<QMUISection<SectionHeader_Act, SectionItem_Attend>> data;

    public ActCardModel() {
    }

    @Override
    public List<QMUISection<SectionHeader_Act, SectionItem_Attend>> getData() {
        // 加载数据
        actList = new ArrayList<>();
        actAttendMap = new HashMap<>();
        prepareData(actList, actAttendMap);
        data = new ArrayList<>();
        wrapData(actList, actAttendMap, data);
        return data;
    }

    @Override
    public List<ActShow> getActList() {
        return actList;
    }

    @Override
    public Map<ActShow, List<AttendShow>> getActAttendMap() {
        return actAttendMap;
    }

    /**
     * 准备初始数据
     * 数据类型为ActShow/AttendShow
     * 需要结合以下函数调用 {@link ActCardModel#wrapData(List, Map, List)}
     *
     * @param actList
     * @param actAttendMap
     */
    private void prepareData(List<ActShow> actList, Map<ActShow, List<AttendShow>> actAttendMap) {
        for (int i = 1; i <= 5; i++) {
            ActShow act = new ActShow();
            act.setName(act.getName() + ", Act.No." + i);
            actList.add(act);
            List<AttendShow> attendList = new ArrayList<>();
            if (i != 3) {
                int jSize = new Random().nextInt(3) + 1;
                for (int j = 1; j <= jSize; j++) {
                    AttendShow attend = new AttendShow();
                    attend.setName(attend.getName() + ", Attend.No." + i);
                    attendList.add(attend);
                }
            }
            actAttendMap.put(act, attendList);
        }
    }

    /**
     * 包装活动与签到数据
     * 将数据载体类包装成展示数据类
     * ActShow -> SectionHeader_Act
     * AttendShow -> SectionItem_Attend
     *
     * @param actList
     * @param actAttendMap
     * @param data
     */
    private void wrapData(List<ActShow> actList, Map<ActShow, List<AttendShow>> actAttendMap,
                          List<QMUISection<SectionHeader_Act, SectionItem_Attend>> data) {
        for (ActShow act : actList) {
            if (actAttendMap.containsKey(act)) {
                data.add(createSection(act, Objects.requireNonNull(actAttendMap.get(act)), true));
            }
        }
    }

    /**
     * 根据ActShow/AttendShow创建SectionHeader_Act/SectionItem_attend
     *
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> createSection(ActShow act, List<AttendShow> attendList, boolean isFold) {
        SectionHeader_Act header = new SectionHeader_Act(act);
        ArrayList<SectionItem_Attend> contents = new ArrayList<>();
        for (AttendShow attend : attendList) {
            contents.add(new SectionItem_Attend(attend));
        }
        QMUISection<SectionHeader_Act, SectionItem_Attend> section = new QMUISection<>(header, contents, isFold);
        // if test load more, you can open the code
        section.setExistAfterDataToLoad(false);
//        section.setExistBeforeDataToLoad(true);
        return section;
    }

}
