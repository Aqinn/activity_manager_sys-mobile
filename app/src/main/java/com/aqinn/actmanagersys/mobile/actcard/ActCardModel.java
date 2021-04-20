package com.aqinn.actmanagersys.mobile.actcard;

import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private ActCardType mType;

    public ActCardModel(ActCardType type) {
        // 加载数据
        actList = new ArrayList<>();
        actAttendMap = new HashMap<>();
        data = new ArrayList<>();
        mType = type;
        if (mType == ActCardType.FLAG_CREATE)
            prepareCreateActTestData();
        else if (mType == ActCardType.FLAG_JOIN)
            prepareJoinActTestData();
    }

    @Override
    public List<QMUISection<SectionHeader_Act, SectionItem_Attend>> getData() {
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

    @Override
    public boolean deleteAct(int sectionHeaderIndex) {
//        actList.remove(section.getHeader().getAct());
//        actAttendMap.remove(section.getHeader().getAct());
//        return data.remove(section);
        try {
            actAttendMap.remove(actList.get(sectionHeaderIndex));
            actList.remove(sectionHeaderIndex);
            data.remove(sectionHeaderIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateAct(int sectionHeaderIndex, ActShow newAct) {
        try {
            ActShow rawAct = data.get(sectionHeaderIndex).getHeader().getAct();
            rawAct.copyOther(newAct);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertAct(int position, ActShow newAct, List<AttendShow> attendList) {
        List<AttendShow> toBeAddAttendList = attendList;
        if (attendList == null)
            toBeAddAttendList = new ArrayList<>();
        createSection(position, newAct, toBeAddAttendList, true);
        return true;
    }

    @Override
    public boolean deleteAttend(int sectionHeaderIndex, int sectionItemIndex) {
//        try {
//            Iterator<QMUISection<SectionHeader_Act, SectionItem_Attend>> it = data.iterator();
//            int sectionIdx = 0;
//            while (it.hasNext()) {
//                QMUISection<SectionHeader_Act, SectionItem_Attend> temp = it.next();
//                if (temp == section) {
//                    it.remove();
//                    break;
//                }
//                sectionIdx++;
//            }
//            ActShow act = section.getHeader().getAct();
//            AttendShow attend = sectionItem.getAttend();
//            if (actAttendMap.containsKey(act)) {
//                Objects.requireNonNull(actAttendMap.get(act)).remove(attend);
//            }
//            createSection(sectionIdx, act, Objects.requireNonNull(actAttendMap.get(act)), true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
        try {
            ActShow act = data.get(sectionHeaderIndex).getHeader().getAct();
            data.remove(sectionHeaderIndex);
            Objects.requireNonNull(actAttendMap.get(act)).remove(sectionItemIndex);
            createSection(sectionHeaderIndex, act, Objects.requireNonNull(actAttendMap.get(act)), false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateAttend() {
        return false;
    }

    @Override
    public boolean insertAttend(int sectionHeaderIndex, boolean isLoadBefore, AttendShow attend) {
        try {
            ActShow act = actList.get(sectionHeaderIndex);
            if (isLoadBefore) {
                Objects.requireNonNull(actAttendMap.get(act)).add(0, attend);
            } else {
                Objects.requireNonNull(actAttendMap.get(act)).add(attend);
            }
            List<SectionItem_Attend> toBeAdd = new ArrayList<>();
            SectionItem_Attend item = new SectionItem_Attend(attend);
            toBeAdd.add(item);
            data.get(sectionHeaderIndex).finishLoadMore(toBeAdd, isLoadBefore, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 准备实验初始数据 - 创建活动
     */
    private void prepareCreateActTestData() {
        for (int i = 1; i <= 10; i++) {
            ActShow act = new ActShow();
            act.setName(act.getName() + ", Act.No." + i);
            List<AttendShow> attendList = new ArrayList<>();
            if (i != 11) {
                int jSize = new Random().nextInt(3) + 1;
                for (int j = 1; j <= jSize; j++) {
                    AttendShow attend = new AttendShow();
                    attend.setName(attend.getName() + ", Attend.No." + i);
                    attendList.add(attend);
                }
            }
            createSection(data.size(), act, attendList, true);
        }
    }

    /**
     * 准备实验初始数据 - 加入活动
     */
    private void prepareJoinActTestData() {
        for (int i = 1; i <= 3; i++) {
            ActShow act = new ActShow();
            act.setName(act.getName() + ", Act.No." + i);
            List<AttendShow> attendList = new ArrayList<>();
            if (i != 11) {
                int jSize = new Random().nextInt(3) + 1;
                for (int j = 1; j <= jSize; j++) {
                    AttendShow attend = new AttendShow();
                    attend.setName(attend.getName() + ", Attend.No." + i);
                    attendList.add(attend);
                }
            }
            createSection(data.size(), act, attendList, true);
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
        return createSection(0, act, attendList, isFold);
    }

    /**
     * 根据ActShow/AttendShow创建SectionHeader_Act/SectionItem_attend
     *
     * @param position
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> createSection(int position, ActShow act, List<AttendShow> attendList, boolean isFold) {
        actList.add(position, act);
        SectionHeader_Act header = new SectionHeader_Act(act);
        ArrayList<SectionItem_Attend> contents = new ArrayList<>();
        for (AttendShow attend : attendList) {
            contents.add(new SectionItem_Attend(attend));
        }
        actAttendMap.put(act, attendList);
        QMUISection<SectionHeader_Act, SectionItem_Attend> section = new QMUISection<>(header, contents, isFold);
        // if test load more, you can open the code
        section.setExistAfterDataToLoad(false);
//        section.setExistBeforeDataToLoad(true);
        data.add(position, section);
        return section;
    }

}
