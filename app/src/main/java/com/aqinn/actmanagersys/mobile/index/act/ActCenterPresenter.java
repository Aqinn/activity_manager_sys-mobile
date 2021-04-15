package com.aqinn.actmanagersys.mobile.index.act;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动中心 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/7 2:24 PM
 */
public class ActCenterPresenter implements IActCenter.Presenter {

    private IActCenter.View mView;

    private Banner mBanner;

    public ActCenterPresenter(IActCenter.View view) {
        mView = view;
    }

    @Override
    public void initBanner(BaseFragment context, Banner banner) {
        mBanner = banner;
        List<Integer> imgList = new ArrayList<>();
        imgList.add(R.drawable.wxgg);
        imgList.add(R.drawable.wxgg);
        imgList.add(R.drawable.wxgg);
        mBanner.setAdapter(new BannerImageAdapter<Integer>(imgList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer drawableId, int position, int size) {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(drawableId));
            }
        }).addBannerLifecycleObserver(context)
                .setIndicator(new CircleIndicator(context.getActivity()));
    }

}
