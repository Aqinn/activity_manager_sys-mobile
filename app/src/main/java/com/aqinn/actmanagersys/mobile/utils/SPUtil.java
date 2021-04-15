package com.aqinn.actmanagersys.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.aqinn.actmanagersys.mobile.base.PublicConfig;

/**
 * 方便操作 SharePreference 的工具类
 *
 * @author Aqinn
 * @date 2021/4/7 2:44 PM
 */
public class SPUtil {

    /**
     * 设置String型变量
     *
     * @param context
     * @param fileName
     * @param key
     * @param val
     */
    public static void setString(Context context, String fileName, String key, String val) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putString(key, val).apply();
    }

    /**
     * 设置Long型变量
     *
     * @param context
     * @param fileName
     * @param key
     * @param val
     */
    public static void setLong(Context context, String fileName, String key, Long val) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putLong(key, val).apply();
    }

    /**
     * 获取String变量
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultVal
     * @return
     */
    public static String getString(Context context, String fileName, String key, String defaultVal) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultVal);
    }

    /**
     * 获取Long变量
     *
     * @param context
     * @param fileName
     * @param key
     * @param defaultVal
     * @return
     */
    public static Long getLong(Context context, String fileName, String key, long defaultVal) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultVal);
    }

    /**
     * 获得登录中的用户token
     *
     * @param context
     * @return
     */
    public static String getLoginToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    /**
     * 获得登录中的用户ID
     *
     * @param context
     * @return
     */
    public static Long getLoginUserID(Context context) {
        SharedPreferences sp = context.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        return sp.getLong("id", PublicConfig.UNKNOWN_USER_ID);
    }

    /**
     * 获得登录中的账号
     *
     * @param context
     * @return
     */
    public static String getLoginAccount(Context context) {
        SharedPreferences sp = context.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        return sp.getString("account", null);
    }

    /**
     * 获得登录中的昵称
     *
     * @param context
     * @return
     */
    public static String getLoginUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        return sp.getString("username", null);
    }

    /**
     * 设置登录用户的token
     *
     * @param context
     * @param token
     */
    public static void setLoginToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        sp.edit()
                .putString("token", token)
                .apply();
    }

    /**
     * 获得记住的账号
     *
     * @param context
     * @return
     */
    public static String getRememberAccount(Context context) {
        SharedPreferences sp = context.getSharedPreferences("remember_user_info", Context.MODE_PRIVATE);
        return sp.getString("account", null);
    }

    /**
     * 获得记住的密码
     *
     * @param context
     * @return
     */
    public static String getRememberPwd(Context context) {
        SharedPreferences sp = context.getSharedPreferences("remember_user_info", Context.MODE_PRIVATE);
        return sp.getString("pwd", null);
    }

    /**
     * 设置登录用户信息
     *
     * @param context
     * @param id
     * @param account
     * @param username
     */
    public static void setLoginUserInfo(Context context, Long id, String account, String username) {
        SharedPreferences sp = context.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);
        sp.edit()
                .putLong("id", id)
                .putString("account", account)
                .putString("username", username)
                .apply();
    }

    /**
     * 设置记住的用户信息
     *
     * @param context
     * @param account
     * @param pwd
     */
    public static void setRememberUserInfo(Context context, String account, String pwd) {
        SharedPreferences sp = context.getSharedPreferences("remember_user_info", Context.MODE_PRIVATE);
        sp.edit()
                .putString("account", account)
                .putString("pwd", pwd)
                .apply();
    }

    /**
     * 登出用户
     *
     * @param context
     */
    public static void loginOut(Context context) {
        setLoginUserInfo(context, PublicConfig.UNKNOWN_USER_ID, null, null);
    }

}
