package com.aqinn.actmanagersys.mobile.base;

import com.aqinn.actmanagersys.mobile.retrofitservice.ActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.AttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.SetupService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserActService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserAttendService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserFeatureService;
import com.aqinn.actmanagersys.mobile.retrofitservice.UserService;
import com.aqinn.actmanagersys.mobile.utils.RetrofitUtil;

/**
 * 基础网络服务类，用于提供Retrofit网络服务
 *
 * @author Aqinn
 * @date 2021/4/15 7:14 PM
 */
public interface BaseNetworkService {

    public default UserService getUserService() {
        return RetrofitUtil.getRetrofit().create(UserService.class);
    }

    public default ActService getActService() {
        return RetrofitUtil.getRetrofit().create(ActService.class);
    }

    public default AttendService getAttendService() {
        return RetrofitUtil.getRetrofit().create(AttendService.class);
    }

    public default UserActService getUserActService() {
        return RetrofitUtil.getRetrofit().create(UserActService.class);
    }

    public default UserAttendService getUserAttendService() {
        return RetrofitUtil.getRetrofit().create(UserAttendService.class);
    }

    public default UserFeatureService getUserFeatureService() {
        return RetrofitUtil.getRetrofit().create(UserFeatureService.class);
    }

    public default SetupService getSetupService() {
        return RetrofitUtil.getRetrofit().create(SetupService.class);
    }

}
