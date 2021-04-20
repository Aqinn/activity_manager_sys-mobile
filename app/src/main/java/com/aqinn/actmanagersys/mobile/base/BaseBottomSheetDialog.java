package com.aqinn.actmanagersys.mobile.base;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

/**
 * 自定义底部弹出视图
 * @author Aqinn
 * @date 2021/4/19 4:05 PM
 */
public abstract class BaseBottomSheetDialog extends QMUIBottomSheet implements BaseNetworkService {

    public BaseBottomSheetDialog(Context context) {
        super(context);
    }

    public BaseBottomSheetDialog(Context context, int style) {
        super(context, style);
    }

}
