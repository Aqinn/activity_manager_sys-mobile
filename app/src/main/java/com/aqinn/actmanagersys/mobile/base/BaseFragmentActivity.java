package com.aqinn.actmanagersys.mobile.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.aqinn.actmanagersys.mobile.retrofitservice.ActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.AttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserAttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserFeatureService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserService;
import com.aqinn.actmanagersys.mobile.utils.RetrofitUtil;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import butterknife.ButterKnife;

/**
 * 自定义FragmentActivity
 * @author Aqinn
 * @date 2021/3/29 11:32 AM
 */
public abstract class BaseFragmentActivity extends QMUIFragmentActivity implements BaseNetworkService{

    private static final String TAG = "BaseFragmentActivity";
    private static final int FACE_PERMISSION_QUEST_CAMERA = 1024;

    public void askForPermission() {
        //检测权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "didnt get permission,ask for it!");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET},
                    FACE_PERMISSION_QUEST_CAMERA);
        }
    }

}
