package org.hades.sue.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class MoreHospitalActivity extends BaseActivity {

    private static final String TAG = MoreHospitalActivity.class.getSimpleName();

    @BindView(R.id.my_title_bar_back)
    BGATitleBar mTitleBar;

    @BindView(R.id.xref_view)
    XRefreshView xRefreshView;

    @BindView(R.id.rv_news_content)
    RecyclerView mNewsContent;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_more_hospital;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

}
