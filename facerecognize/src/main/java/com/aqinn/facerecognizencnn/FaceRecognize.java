package com.aqinn.facerecognizencnn;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

/**
 * @author Aqinn
 * @date 2021/1/8 11:58 PM
 */
public class FaceRecognize {

    // *************************** 人脸检测 ***************************

    // 初始化人脸检测模型
    // 加载模型接口 AssetManager用于加载assert中的权重文件
    public native int initRetainFace(AssetManager mgr);

    // 从 Bitmap 中检测最大人脸
    // 返回值为 检测框的左上右下值 + 五个面部关键点的坐标
    public native float[] detectFromBitmap(Bitmap bitmap);

    // 从 Bitmap 中检测多张人脸
    // 返回值为 多张脸的 检测框的左上右下值 + 五个面部关键点的坐标
    public native float[][] detectMultiFaceFromBitmap(Bitmap bitmap);
    // 先以固定倍率缩小 Bitmap，检测成功后将坐标放大固定倍率复原
    public native float[][] detectTest(Bitmap bitmap, int w, int h, int ratio);

    // 从视频流检测人脸
    public native float[] detectFromStream(byte[] yuv420sp, int width, int height, int view_width, int view_height, int rotate);

    // 反初始化人脸检测模型
    public native void deinitRetainFace();

    // *************************** 人脸识别 ****************************

    // 初始化人脸识别模型
    public native boolean initMobileFacenet(String modelPath);

    // 人脸识别
    public native float[] recognize(byte[] faceData, int w, int h, int[] landmarks);

    // 人脸验证
    public native double compare(float[] feature1, float[] feature2);

    // 反初始化人脸检测模型
    public native void deinitMobileFacenet();

    static {
        System.loadLibrary("Face");
    }

}
