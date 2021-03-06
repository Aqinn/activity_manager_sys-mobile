package com.aqinn.actmanagersys.mobile.base;

/**
 * 公共配置类
 * @author Aqinn
 * @date 2021/3/29 1:15 PM
 */
public class PublicConfig {

    /**
     * 是否debug模式？可以决定某些Toast的显示与否
     */
    public static final boolean isDebug = true;

    /**
     * 未知用户ID
     * 代表着未登录
     */
    public static final Long UNKNOWN_USER_ID = -1L;

    /**
     * 请求的根URL
     */
//    public static final String BASE_URL = "http://121.5.162.173:8080/";
    public static final String BASE_URL = "http://192.168.43.78:8080/";

    /**
     * 弹窗样式
     */
    public static int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    /**
     * 临时的TAG
     */
    public static final String TEMP_TAG = "temp_tag";

    /**
     * 同一个人的两条人脸特征向量需要满足的阈值
     */
    public static final double SAME_FACE_THRESHOLD = 0.5;

}
