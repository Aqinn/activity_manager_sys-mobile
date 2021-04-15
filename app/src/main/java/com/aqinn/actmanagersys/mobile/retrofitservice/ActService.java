package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 活动相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 4:42 PM
 */
public interface ActService {

    /**
     * 创建活动
     *
     * @param name
     * @param desc
     * @param location
     * @param startTime
     * @param endTime
     * @return
     */
    @FormUrlEncoded
    @POST("/act")
    Observable<ApiResult<ActShow>> createAct(@Field("name") String name, @Field("desc") String desc,
                                             @Field("location") String location, @Field("startTime") String startTime,
                                             @Field("endTime") String endTime);

    /**
     * 修改活动
     *
     * @param name
     * @param desc
     * @param location
     * @param startTime
     * @param endTime
     * @return
     */
    @FormUrlEncoded
    @PUT("/act")
    Observable<ApiResult> updateAct(@Field("name") String name, @Field("desc") String desc,
                                    @Field("location") String location, @Field("startTime") String startTime,
                                    @Field("endTime") String endTime);

    /**
     * 开启活动
     *
     * @param actId
     * @return
     */
    @PUT("act/{actId}/start")
    Observable<ApiResult> startAct(@Path("actId") Long actId);

    /**
     * 停止活动
     *
     * @param actId
     * @return
     */
    @PUT("act/{actId}/stop")
    Observable<ApiResult> stopAct(@Path("actId") Long actId);


}
