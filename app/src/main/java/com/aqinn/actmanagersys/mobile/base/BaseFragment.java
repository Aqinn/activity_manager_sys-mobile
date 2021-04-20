package com.aqinn.actmanagersys.mobile.base;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.aqinn.actmanagersys.mobile.retrofitservice.ActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.AttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserAttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserFeatureService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserService;
import com.aqinn.actmanagersys.mobile.utils.RetrofitUtil;
import com.qmuiteam.qmui.arch.QMUIFragment;

/**
 * 自定义Fragment
 *
 * @author Aqinn
 * @date 2021/3/29 5:10 PM
 */
public abstract class BaseFragment extends QMUIFragment implements BaseNetworkService {

    private static final String TAG = "BaseFragment";

    protected final void fitSystemWindow(BaseFragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = fragment.getActivity().getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = fragment.getActivity().getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags = 0;
            if (isBlack) {
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            Log.d(TAG, "changeStatusBarTextColor before: " + decor.getSystemUiVisibility());
            decor.setSystemUiVisibility(flags);
            Log.d(TAG, "changeStatusBarTextColor after: " + decor.getSystemUiVisibility());
        }
    }

    public void comeIndexChangeStatusBarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = requireActivity().getWindow().getDecorView();
            int flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decor.setSystemUiVisibility(flags);
        }
    }

    public void leaveIndexChangeStatusBarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = requireActivity().getWindow().getDecorView();
            int flags = 0x500;
            decor.setSystemUiVisibility(flags);
        }
    }

}

