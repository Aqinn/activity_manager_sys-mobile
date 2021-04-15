package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.AttendShow;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 签到相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 4:54 PM
 */
public interface AttendService {

    /**
     * 创建签到
     *
     * @param actId
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("/attend/{actId}")
    Observable<ApiResult<AttendShow>> createAttend(@Path("actId") Long actId, @Field("startTime") String startTime,
                                                   @Field("endTime") String endTime, @Field("type") Integer type);

    /**
     * 修改签到时间
     *
     * @param attendId
     * @param startTime
     * @param endTime
     * @return
     */
    @FormUrlEncoded
    @PUT("/attend/{attendId}/time")
    Observable<ApiResult> updateAttendTime(@Path("attendId") Long attendId, @Field("startTime") String startTime,
                                           @Field("endTime") String endTime);

    /**
     * 修改签到类型
     *
     * @param actId
     * @param type
     * @return
     */
    @FormUrlEncoded
    @PUT("/attend/{attendId}/type")
    Observable<ApiResult> updateAttendType(@Path("attendId") Long actId, @Field("type") Integer type);

}
