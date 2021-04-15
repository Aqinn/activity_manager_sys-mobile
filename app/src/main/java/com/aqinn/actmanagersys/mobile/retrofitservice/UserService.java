package com.aqinn.actmanagersys.mobile.retrofitservice;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.LoginResult;
import com.aqinn.actmanagersys.mobile.model.User;
import com.aqinn.actmanagersys.mobile.model.RegisterResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * 用户相关的 Retrofit Service
 *
 * @author Aqinn
 * @date 2021/4/15 3:51 PM
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param account
     * @param pwd
     * @param name
     * @param contact
     * @param sex
     * @param intro
     * @return
     */
    @FormUrlEncoded
    @POST("/user/register")
    Observable<ApiResult<RegisterResult>> register(@Field("account") String account, @Field("pwd") String pwd,
                                                   @Field("name") String name, @Field("contact") String contact,
                                                   @Field("sex") Integer sex, @Field("intro") String intro);

    /**
     * 用户登录
     *
     * @param account
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<ApiResult<LoginResult>> login(@Field("account") String account, @Field("pwd") String pwd);

    /**
     * 查询用户
     *
     * @return
     */
    @GET("/user")
    Observable<ApiResult<User>> queryUser();

    /**
     * 更新用户
     *
     * @param account
     * @param pwd
     * @param name
     * @param contact
     * @param sex
     * @param intro
     * @return
     */
    @FormUrlEncoded
    @PUT("/user")
    Observable<ApiResult> updateUser(@Field("account") String account, @Field("pwd") String pwd,
                                     @Field("name") String name, @Field("contact") String contact,
                                     @Field("sex") Integer sex, @Field("intro") String intro);

}
