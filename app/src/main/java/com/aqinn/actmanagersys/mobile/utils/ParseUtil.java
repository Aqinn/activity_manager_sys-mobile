package com.aqinn.actmanagersys.mobile.utils;

/**
 * 工具类 - 数据解析
 *
 * @author Aqinn
 * @date 2021/3/29 1:45 PM
 */
public class ParseUtil {

    /**
     * int 转 字符串形式的性别
     *
     * @param sex
     * @return
     */
    public static final String int2Sex(int sex) {
        switch (sex) {
            case 0:
                return "女";
            case 1:
                return "男";
            default:
                return "未知";
        }
    }

    /**
     * 字符串形式的性别 转 int
     *
     * @param sex
     * @return
     */
    public static final int sex2Int(String sex) {
        switch (sex) {
            case "女":
                return 0;
            case "男":
                return 1;
            default:
                return -1;
        }
    }

    /**
     * int 转 字符串形式的联系方式
     *
     * @param contactType
     * @return
     */
    public static final String int2ContactType(int contactType) {
        switch (contactType) {
            case 1:
                return "微信";
            case 2:
                return "邮箱";
            case 3:
                return "电话";
            default:
                return "未知";
        }
    }

    /**
     * 字符串形式的联系方式 转 int
     *
     * @param contactType
     * @return
     */
    public static final int ContactType2Int(String contactType) {
        switch (contactType) {
            case "微信":
                return 1;
            case "邮箱":
                return 2;
            case "电话":
                return 3;
            default:
                return -1;
        }
    }

}
