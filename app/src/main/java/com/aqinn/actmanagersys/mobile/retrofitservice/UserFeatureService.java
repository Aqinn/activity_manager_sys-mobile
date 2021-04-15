package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 用户特征相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 5:32 PM
 */
public interface UserFeatureService {

    @FormUrlEncoded
    @POST("/userfeature")
    Observable<ApiResult> faceCollect(@Field("f1") String f1, @Field("f2") String f2,
                                      @Field("f3") String f3, @Field("f4") String f4);

}
