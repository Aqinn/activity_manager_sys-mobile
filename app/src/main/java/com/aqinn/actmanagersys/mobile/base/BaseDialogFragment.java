package com.aqinn.actmanagersys.mobile.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.aqinn.actmanagersys.mobile.retrofitservice.ActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.AttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserAttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserFeatureService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserService;
import com.aqinn.actmanagersys.mobile.utils.RetrofitUtil;

/**
 * 自定义弹出式Fragment
 *
 * @author Aqinn
 * @date 2021/3/29 5:12 PM
 */
public abstract class BaseDialogFragment extends DialogFragment implements BaseNetworkService {

    protected Dialog mDialog;

    protected void initDialog() {
        mDialog = getDialog();
        Window win = getDialog().getWindow();
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = win.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        win.setAttributes(params);
    }

}
