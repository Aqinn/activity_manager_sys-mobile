package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.JoinActResult;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 用户活动相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 5:03 PM
 */
public interface UserActService {

    /**
     * 加入活动
     *
     * @param code
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("/useract")
    Observable<ApiResult<JoinActResult>> joinAct(@Field("code") String code, @Field("pwd") String pwd);

    /**
     * 退出活动
     *
     * @param actId
     * @return
     */
    @DELETE("/useract/{actId}")
    Observable<ApiResult> quitAct(@Path("actId") Long actId);

}
