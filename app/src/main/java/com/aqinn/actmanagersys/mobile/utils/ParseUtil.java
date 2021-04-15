package com.aqinn.actmanagersys.mobile.utils;

import android.graphics.Bitmap;

import com.aqinn.facerecognizencnn.FaceInfo;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 工具类 - 数据解析
 * TODO 本类中使用到的中文字符串为魔法值，在做国际化的时候肯定会遇到问题，可以调用时传入上下文解决
 *
 * @author Aqinn
 * @date 2021/3/29 1:45 PM
 */
public class ParseUtil {

    private static Map<Integer, String> attendTypeMap;
    private static final Integer SELF_ATTEND_MASK = 1 << 0;
    private static final Integer VIDEO_ATTEND_MASK = 1 << 1;
    public static final String SELF_ATTEND_DESC = "自助签到";
    public static final String VIDEO_ATTEND_DESC = "视频签到";
    public static final String SEX_MALE = "男";
    public static final String SEX_FEMALE = "女";
    public static final String UNKNOWN = "未知";
    public static final String WECHAT = "微信";
    public static final String TELEPHONE = "电话";
    public static final String EMAIL = "邮箱";


    static {
        attendTypeMap = new HashMap<>();
        attendTypeMap.put(SELF_ATTEND_MASK, SELF_ATTEND_DESC);
        attendTypeMap.put(VIDEO_ATTEND_MASK, VIDEO_ATTEND_DESC);
    }

    /**
     * int 转 字符串形式的性别
     *
     * @param sex
     * @return
     */
    public static String int2Sex(int sex) {
        switch (sex) {
            case 0:
                return SEX_FEMALE;
            case 1:
                return SEX_MALE;
            default:
                return UNKNOWN;
        }
    }

    /**
     * 字符串形式的性别 转 int
     *
     * @param sex
     * @return
     */
    public static int sex2Int(String sex) {
        switch (sex) {
            case SEX_FEMALE:
                return 0;
            case SEX_MALE:
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
    public static String int2ContactType(int contactType) {
        switch (contactType) {
            case 1:
                return WECHAT;
            case 2:
                return EMAIL;
            case 3:
                return TELEPHONE;
            default:
                return UNKNOWN;
        }
    }

    /**
     * 字符串形式的联系方式 转 int
     *
     * @param contactType
     * @return
     */
    public static int ContactType2Int(String contactType) {
        switch (contactType) {
            case WECHAT:
                return 1;
            case EMAIL:
                return 2;
            case TELEPHONE:
                return 3;
            default:
                return -1;
        }
    }

    /**
     * 解析int里的签到类型信息
     *
     * @param type int型的签到类型信息
     * @return 字符串形式的签到类型描述
     */
    public static String getAttendType(Integer type) {
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<Integer, String>> it = attendTypeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> e = it.next();
            if ((e.getKey() & type) == e.getKey()) {
                sb.append(e.getValue());
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否自助类型的签到
     *
     * @param type
     * @return
     */
    public static boolean isSelfAttend(Integer type) {
        return (SELF_ATTEND_MASK & type) == SELF_ATTEND_MASK;
    }

    /**
     * 判断是否视频签到类型的签到
     *
     * @param type
     * @return
     */
    public static boolean isVideoAttend(Integer type) {
        return (VIDEO_ATTEND_MASK & type) == VIDEO_ATTEND_MASK;
    }

    /**
     * 通过一个数组构建int型的签到类型信息
     * 原理如下:
     * 数组中有效的值为数组长度以及值为1的数组元素
     * 当第i个位置上的元素值为1时，代表着1 << i这个签到信息MASK需要被添加到结果中
     * 比如数组{1, 1}会被识别成 (1 << 0) + (1 << 1) 即 (11)2 => (3)10
     *
     * @param types
     * @return
     */
    public static Integer buildAttendType(Integer... types) {
        int len;
        if (types == null || (len = types.length) == 0)
            return 0;
        int res = 0;
        for (int i = 0; i < len; i++) {
            // 该位有效
            if (types[i] == 1) {
                res += 1 << i;
            }
        }
        return res;
    }

    /**
     * 提取Bitmap中的像素点
     *
     * @param image
     * @return
     */
    public static byte[] getPixelsRGBA(Bitmap image) {
        // calculate how many bytes our image consists of
        int bytes = image.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes); // Create a new buffer
        image.copyPixelsToBuffer(buffer); // Move the byte data to the buffer
        byte[] temp = buffer.array(); // Get the underlying array containing the
        return temp;
    }

    /**
     * 将数组形式的浮点数转换成FaceInfo
     *
     * @param arr
     * @return
     */
    public static FaceInfo floatArr2FaceInfo(float arr[]) {
        FaceInfo faceInfo = new FaceInfo();
        try {
            faceInfo.x1 = arr[0];
            faceInfo.y1 = arr[1];
            faceInfo.x2 = arr[2];
            faceInfo.y2 = arr[3];
            faceInfo.keypoints = new float[5][2];
            faceInfo.keypoints[0][0] = arr[4];
            faceInfo.keypoints[0][1] = arr[5];
            faceInfo.keypoints[1][0] = arr[6];
            faceInfo.keypoints[1][1] = arr[7];
            faceInfo.keypoints[2][0] = arr[8];
            faceInfo.keypoints[2][1] = arr[9];
            faceInfo.keypoints[3][0] = arr[10];
            faceInfo.keypoints[3][1] = arr[11];
            faceInfo.keypoints[4][0] = arr[12];
            faceInfo.keypoints[4][1] = arr[13];
            faceInfo.landmarks = null;
            faceInfo.score = 0;
        } catch (Exception e) {
            faceInfo = null;
        }
        return faceInfo;
    }

    /**
     * 从FaceInfo中获取人脸关键点坐标
     *
     * @param faceInfo
     * @return
     */
    public static int[] getUsefulLandmarksFromFaceInfo(FaceInfo faceInfo) {
        int arr[] = new int[10];
        arr[0] = (int) faceInfo.keypoints[0][0];
        arr[1] = (int) faceInfo.keypoints[1][0];
        arr[2] = (int) faceInfo.keypoints[2][0];
        arr[3] = (int) faceInfo.keypoints[3][0];
        arr[4] = (int) faceInfo.keypoints[4][0];
        arr[5] = (int) faceInfo.keypoints[0][1];
        arr[6] = (int) faceInfo.keypoints[1][1];
        arr[7] = (int) faceInfo.keypoints[2][1];
        arr[8] = (int) faceInfo.keypoints[3][1];
        arr[9] = (int) faceInfo.keypoints[4][1];
        return arr;
    }

    /**
     * 将浮点数组拼接成 "," 分隔的字符串形式
     * @param arr
     * @return
     */
    public static String arr2String(float arr[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                sb.append(arr[i]);
                break;
            }
            sb.append(arr[i]).append(",");
        }
        return sb.toString();
    }

    /**
     * 将 "," 分隔的字符串形式转换成浮点数组
     * @param str
     * @return
     */
    public static float[] string2Arr(String str) {
        String fArr[] = str.split(",");
        if (fArr == null || fArr.length != 128) {
            return null;
        }
        float ff[] = new float[128];
        for (int i = 0; i < fArr.length; i++) {
            ff[i] = Float.parseFloat(fArr[i]);
        }
        return ff;
    }

}
