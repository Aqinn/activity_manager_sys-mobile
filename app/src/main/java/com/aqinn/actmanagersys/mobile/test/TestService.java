package com.aqinn.actmanagersys.mobile.test;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.LoginResult;
import com.aqinn.actmanagersys.mobile.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 测试用的Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 3:51 PM
 */
public interface TestService {

    @GET("/user")
    Observable<ApiResult<User>> getUser();

    @FormUrlEncoded
    @POST("/user/login")
    Observable<ApiResult<LoginResult>> login(@Field("account") String account, @Field("pwd") String pwd);

}
