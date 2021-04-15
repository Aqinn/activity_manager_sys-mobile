package com.aqinn.actmanagersys.mobile.utils;

import android.util.Log;

import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.interceptor.HttpTokenInterceptor;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit网络请求工具类
 * @author Aqinn
 * @date 2021/4/15 3:25 PM
 */
public class RetrofitUtil {

    private static final String TAG = "RetrofitUtil";

    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();
    //    private static WebSocket webSocket = new OkHttpClient().newWebSocket();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(PublicConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build();

    private static OkHttpClient getOkHttpClient() {
        return builder
                .addInterceptor(buildHttpLoggerInterceptor())
                .addInterceptor(buildHttpTokenInterceptor())
                .connectTimeout(3, TimeUnit.SECONDS).build();
    }

    /**
     * 创建可以用于打印请求参数的日志的 Builder
     * （因为使用了 Rxjava + Retrofit 所以不可以直接打印 Request 的参数）
     *
     * @return
     */
    private static HttpLoggingInterceptor buildHttpLoggerInterceptor() {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.d(TAG, "log: Http 参数: " + s);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    private static HttpTokenInterceptor buildHttpTokenInterceptor() {
        HttpTokenInterceptor interceptor = new HttpTokenInterceptor();
        return interceptor;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
