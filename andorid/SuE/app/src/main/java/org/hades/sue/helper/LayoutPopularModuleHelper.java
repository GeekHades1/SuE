package org.hades.sue.helper;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hades.sue.R;
import org.hades.sue.view.PopularTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taro on 16/10/17.
 */

public class LayoutPopularModuleHelper {
    @BindView(R.id.layout_popular_title)
    PopularTitleView mTitleView;
    @BindView(R.id.rv_layout_popular_module)
    public RecyclerView mRvContent;

    private View mContentView = null;

    public LayoutPopularModuleHelper(@NonNull View contentView) {
        mContentView = contentView;
        ButterKnife.bind(this, contentView);
    }

    public void setVisibility(int visibility) {
        mContentView.setVisibility(visibility);
    }

    public void setTitle(@StringRes int id) {
        mTitleView.setTitle(id);
    }

    public void setMore(@StringRes int id) {
        mTitleView.setMore(id);
    }

    public void setTitle(String title) {
        mTitleView.setTitle(title);
    }

    public void setMore(String more) {
        mTitleView.setMore(more);
    }

    public void setMoreInvisible() {
        mTitleView.setMoreVisibility(false);
    }

    public void setMoreVisible() {
        mTitleView.setMoreVisibility(true);
    }

    public boolean isMoreIconOnClick(@NonNull View view) {
        return mTitleView.isMoreClickListenr(view);
    }

    public void setMoreClickListener(PopularTitleView.OnTitleMoreClickListener listener) {
        mTitleView.setOnMoreClickListener(listener);
    }

    public void setPrimaryColorRes(@ColorRes int colorId) {
        mTitleView.setAccentColorTint(colorId);
    }

    public void setTagColor(@ColorRes int colorId){
        mTitleView.setTagColor(colorId);
    }

    public void setRecyclerViewLinearLayout(@NonNull Context context, boolean isHorizontal) {
        int orientation = isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
        mRvContent.setLayoutManager(layoutManager);
    }

    public void setRecyclerViewGridLayout(@NonNull Context context, boolean isHorizontal, int spanCount) {
        int orientation = isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL;
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount, orientation, false);
        mRvContent.setLayoutManager(layoutManager);
    }
}
