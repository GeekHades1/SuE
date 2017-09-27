package org.hades.sue.fragment;

import android.view.View;

import org.hades.sue.R;
import org.hades.sue.base.BaseFragment;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by Hades on 2017/9/21.
 */

public class MineFragment extends BaseFragment {

    private BGATitleBar mTitleBar;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
        mTitleBar = (BGATitleBar) mHomeActivity.getTitleBar();
        initBar();
    }

    @Override
    public void initData() {

    }

    private void initBar() {
        mTitleBar.setTitleText("我的");
        mTitleBar.getLeftCtv().setVisibility(View.GONE);
        mTitleBar.getRightCtv().setVisibility(View.GONE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            initBar();
        }
        super.onHiddenChanged(hidden);
    }
}
