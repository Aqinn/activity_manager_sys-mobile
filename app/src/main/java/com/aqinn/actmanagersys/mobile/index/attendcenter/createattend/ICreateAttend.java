package com.aqinn.actmanagersys.mobile.index.attendcenter.createattend;

import android.widget.TextView;

/**
 * 创建签到 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/16 8:38 PM
 */
@Deprecated
public interface ICreateAttend {

    interface View {
    }

    interface Presenter {
        void initTimePicker(CreateAttendFragment context, TextView tvStartTime, TextView tvEndTime);
    }

    interface Model {
    }

}
