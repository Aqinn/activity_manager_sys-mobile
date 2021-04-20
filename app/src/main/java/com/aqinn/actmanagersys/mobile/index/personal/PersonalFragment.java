package com.aqinn.actmanagersys.mobile.index.personal;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.facecollect.FaceCollectActivity;
import com.aqinn.actmanagersys.mobile.login.LoginActivity;
import com.aqinn.actmanagersys.mobile.model.User;
import com.aqinn.actmanagersys.mobile.utils.SPUtil;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心 - View
 *
 * @author Aqinn
 * @date 2021/4/2 1:32 PM
 */
@Deprecated
public class PersonalFragment extends BaseFragment {

//    @BindView(R.id.topbar)
//    QMUITopBarLayout topbar;
    @BindView(R.id.iv_head)
    QMUIRadiusImageView2 ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.iv_contact_type)
    ImageView ivContactType;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.iv_intro)
    ImageView ivIntro;
    @BindView(R.id.tv_intro)
    TextView tvIntro;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_personal, null);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
//        topbar.setTitle(getResources().getString(R.string.personal));
//        topbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showBottomSheetList();
//                    }
//                });
        if (PublicConfig.isDebug) {
            User user = new User("zchin", "biubiubiu", "Aqinn", 1, "_aqinn", 1, "我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！我是一个兵，来自老百姓！");
            tvName.setText(user.getName());
            switch (user.getSex()) {
                case 0:
                    ivSex.setImageDrawable(getResources().getDrawable(R.drawable.female));
                    break;
                case 1:
                    ivSex.setImageDrawable(getResources().getDrawable(R.drawable.male));
                    break;
            }
            switch (user.getContactType()) {
                case 1:
                    ivContactType.setImageDrawable(getResources().getDrawable(R.drawable.icon_wechat));
                    break;
                case 2:
                    ivContactType.setImageDrawable(getResources().getDrawable(R.drawable.icon_email));
                    break;
                case 3:
                    ivContactType.setImageDrawable(getResources().getDrawable(R.drawable.icon_telephone));
                    break;
            }
            tvContact.setText(user.getContact());
            tvIntro.setText(user.getIntro());
        }
    }

    /**
     * 弹出底部功能菜单
     */
    private void showBottomSheetList() {
        new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
                .addItem("编辑信息")
                .addItem("人脸采集")
                .addItem("退出登录")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        switch (position) {
                            case 0:
                                // TODO 编辑信息
                                if (PublicConfig.isDebug)
                                    Toast.makeText(getActivity(), "Test: 编辑信息", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                // TODO 人脸采集
                                if (PublicConfig.isDebug)
                                    Toast.makeText(getActivity(), "Test: 人脸采集", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), FaceCollectActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                if (PublicConfig.isDebug)
                                    Toast.makeText(getActivity(), "Test: 退出登录", Toast.LENGTH_SHORT).show();
                                Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intentToLogin);
                                getActivity().finish();
                                SPUtil.loginOut(getActivity());
                                break;
                            default:
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

}
