package com.aqinn.actmanagersys.mobile.base.QMUI;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;

/**
 * 带有装饰区布局片段的列表
 *
 * @author Aqinn
 * @date 2021/4/17 3:26 PM
 */
public class QDListWithDecorationSectionLayoutFragment extends QDBaseSectionLayoutFragment {

    @Override
    protected QMUIStickySectionAdapter<SectionHeader, SectionItem, QMUIStickySectionAdapter.ViewHolder> createAdapter() {
        return new QDListWithDecorationSectionAdapter(this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
    }

}
