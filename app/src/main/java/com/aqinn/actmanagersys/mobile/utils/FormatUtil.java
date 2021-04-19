package com.aqinn.actmanagersys.mobile.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 工具类 - 校验字符串的合法性
 *
 * @author Aqinn
 * @date 2021/3/29 1:04 PM
 */
public class FormatUtil {

    private static final String accountRegExp = "^[a-zA-Z_][0-9a-zA-Z_]{2,19}$";
    private static final String pwdRegExp = "^[0-9a-zA-Z_]{6,20}$";
    private static final String DATE_FORMAT_PATTERN_YMD = "yyyy-MM-dd";
    private static final String DATE_FORMAT_PATTERN_YMD_HM = "yyyy-MM-dd HH:mm";

    /**
     * 校验账号格式
     *
     * @param account
     * @return
     */
    public static boolean verifyAccount(String account) {
        if (account == null || account.isEmpty() || account.contains(" "))
            return false;
        return account.matches(accountRegExp);
    }

    /**
     * 校验密码格式
     *
     * @param pwd
     * @return
     */
    public static boolean verifyPwd(String pwd) {
        if (pwd == null || pwd.isEmpty() || pwd.contains(" "))
            return false;
        return pwd.matches(pwdRegExp);
    }

    /**
     * 校验活动名称
     *
     * @param actName
     * @return
     */
    public static boolean verifyActName(String actName) {
        if (actName == null || actName.length() > 20)
            return false;
        return true;
    }

    /**
     * 校验活动定位
     *
     * @param actLocation
     * @return
     */
    public static boolean verifyActLocation(String actLocation) {
        if (actLocation == null || actLocation.length() > 50)
            return false;
        return true;
    }

    /**
     * 校验活动描述
     *
     * @param actDesc
     * @return
     */
    public static boolean verifyActDesc(String actDesc) {
        if (actDesc == null || actDesc.length() > 500)
            return false;
        return true;
    }

    /**
     * 校验活动时间的合法性
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean verifyActTime(String startTime, String endTime) {
        long startTimeLong = str2Long(startTime, true);
        long endTimeLong = str2Long(endTime, true);
        if (startTimeLong == 0 || endTimeLong == 0)
            return false;
        if (startTimeLong > endTimeLong)
            return false;
        return true;
    }

    /**
     * 时间戳转字符串
     *
     * @param timestamp     时间戳
     * @param isPreciseTime 是否包含时分
     * @return 格式化的日期字符串
     */
    public static String long2Str(long timestamp, boolean isPreciseTime) {
        return long2Str(timestamp, getFormatPattern(isPreciseTime));
    }

    /**
     * 时间戳转字符串
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    private static String long2Str(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 字符串转时间戳
     *
     * @param dateStr       日期字符串
     * @param isPreciseTime 是否包含时分
     * @return 时间戳
     */
    public static long str2Long(String dateStr, boolean isPreciseTime) {
        return str2Long(dateStr, getFormatPattern(isPreciseTime));
    }

    /**
     * 字符串转时间戳
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    private static long str2Long(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(dateStr).getTime();
        } catch (Throwable ignored) {
        }
        return 0;
    }

    /**
     * 获得时间戳格式
     *
     * @param showSpecificTime
     * @return
     */
    private static String getFormatPattern(boolean showSpecificTime) {
        if (showSpecificTime) {
            return DATE_FORMAT_PATTERN_YMD_HM;
        } else {
            return DATE_FORMAT_PATTERN_YMD;
        }
    }

}
