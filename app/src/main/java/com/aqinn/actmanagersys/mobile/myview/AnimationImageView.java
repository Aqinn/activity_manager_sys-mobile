package com.aqinn.actmanagersys.mobile.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * 可实现淡入淡出动画的ImageView
 *
 * @author Aqinn
 * @date 2021/4/18 11:45 AM
 */
@SuppressLint("AppCompatCustomView")
public class AnimationImageView extends ImageView {

    public AnimationImageView(Context context) {
        super(context);
    }

    public AnimationImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 淡出
     */
    public void disappear() {
        switchWithAnim(1, 0);
    }

    /**
     * 淡入
     */
    public void appear() {
        switchWithAnim(0, 1);
    }

    /**
     * 执行动画实现图片的切换
     */
    private void switchWithAnim(int from, int to) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(from, to);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(800);
        startAnimation(alphaAnimation);
    }

}
