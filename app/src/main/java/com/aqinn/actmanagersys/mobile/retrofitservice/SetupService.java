package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 获取初始列表数据相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 8:35 PM
 */
public interface SetupService {

    /**
     * 获取创建的活动列表
     * @return
     */
    @GET("/setup/acts/create")
    Observable<ApiResult<List<ActShow>>> getCreateActs();

    /**
     * 获取参与的活动列表
     * @return
     */
    @GET("/setup/acts/join")
    Observable<ApiResult<List<ActShow>>> getJoinActs();

    /**
     * 获取创建的签到列表
     * @return
     */
    @GET("/setup/attends/create")
    Observable<ApiResult<List<AttendShow>>> getCreateAttends();

    /**
     * 获取参与的签到列表
     * @return
     */
    @GET("/setup/attends/join")
    Observable<ApiResult<List<AttendShow>>> getJoinAttends();

}
