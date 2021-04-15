package com.aqinn.actmanagersys.mobile.interceptor;

import android.util.Log;

import com.aqinn.actmanagersys.mobile.base.BaseApplication;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.utils.SPUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http Token拦截器
 *
 * @author Aqinn
 * @date 2021/4/15 3:35 PM
 */
public class HttpTokenInterceptor implements Interceptor {

    private static final String TAG = "HttpTokenInterceptor";

    // 注册URL
    private static final String REGISTER_URL = PublicConfig.BASE_URL + "user/register";

    // 登录URL
    private static final String LOGIN_URL = PublicConfig.BASE_URL + "user/login";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        // 如果是注册和登录就不需要加入token，否则加入
        String url = request.url().toString();
        if (!url.equals(REGISTER_URL) && !url.equals(LOGIN_URL)) {
            String token = SPUtil.getLoginToken(BaseApplication.getContext());
            // 重新构建一个Request
            if (token != null) {
                HttpUrl.Builder authorizedUrlBuilder = request.url()
                        .newBuilder()
                        .scheme(request.url().scheme())
                        .host(request.url().host());

                request = request.newBuilder()
                        .addHeader("token", token)
                        .method(request.method(), request.body())
                        .url(authorizedUrlBuilder.build())
                        .build();
            }
        }
        Log.d(TAG, url);
        Response response = chain.proceed(request);
        return response;
    }

}
