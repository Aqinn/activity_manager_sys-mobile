package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.AttendCount;
import com.aqinn.actmanagersys.mobile.model.AttendResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 用户签到相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 5:14 PM
 */
public interface UserAttendService {

    /**
     * 自助签到
     *
     * @param attendId
     * @param feature
     * @return
     */
    @FormUrlEncoded
    @POST("/userattend/selfattend/{attendId}")
    Observable<ApiResult> selfAttend(@Path("attendId") Long attendId, @Field("feature") String feature);

    /**
     * 视频签到
     *
     * @param attendId
     * @param feature
     * @return
     */
    @FormUrlEncoded
    @POST("/userattend/videoattend/{attendId}")
    Observable<ApiResult<AttendResult>> videoAttend(@Path("attendId") Long attendId, @Field("feature") String feature);

    /**
     * 获取签到人数
     *
     * @param attendId
     * @return
     */
    @GET("/userattend/{attendId}")
    Observable<ApiResult<AttendCount>> getAttendCount(@Path("attendId") Long attendId);

    /**
     * 获取特定时间戳后的自助签到信息
     *
     * @param attendId
     * @param timestamp
     * @return
     */
    @GET("/userattend/selfattend/{attendId}/{timestamp}")
    Observable<ApiResult<List<AttendResult>>> getSelfAttendAfterTimestamp(@Path("attendId") Long attendId, @Path("timestamp") Long timestamp);

    /**
     * 获取特定时间戳后的视频签到信息
     *
     * @param attendId
     * @param timestamp
     * @return
     */
    @GET("/userattend/videoattend/{attendId}/{timestamp}")
    Observable<ApiResult<List<AttendResult>>> getVideoAttendAfterTimestamp(@Path("attendId") Long attendId, @Path("timestamp") Long timestamp);

}
