package org.hades.sue.activity;

import android.app.ProgressDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;
import com.taro.headerrecycle.helper.RecycleViewOnClickHelper;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.adapter.MoreHospitalAdapterOption;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.bean.HospitalBean;
import org.hades.sue.utils.POIUtils;
import org.hades.sue.utils.SnackUtils;
import org.hades.sue.utils.Values;
import org.hades.sue.view.SmileyHeaderView;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class MoreHospitalActivity extends BaseActivity implements
POIUtils.HospitalCallBack{

    private static final String TAG = MoreHospitalActivity.class.getSimpleName();

    @BindView(R.id.my_title_bar_back)
    BGATitleBar mTitleBar;

    @BindView(R.id.xref_view)
    XRefreshView xRefreshView;

    @BindView(R.id.rv_content)
    RecyclerView mContent;

    SimpleRecycleAdapter<HospitalBean> mAdapter;
    RecycleViewOnClickHelper clickHelper;

    ProgressDialog dialog ;

    int page = 0;
    int count = 10; // 每次读取条数

    boolean isLoadMore = false;

    POIUtils poi = null;

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
        initBar();
        initRefView();
        GridLayoutManager manager = new GridLayoutManager(this, 2
                , GridLayout.VERTICAL, false);
        mContent.setLayoutManager(manager);
        xRefreshView.startRefresh();
    }

    @Override
    public void initData() {
        poi = new POIUtils(this,this);
        poi.getPOI("医院",
                App.mShareP.getString(Values.LAST_LOCATION,""),
                page, count);
    }


    private void initBar() {
        mTitleBar.setTitleText(R.string.title_more_hospital);
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                MoreHospitalActivity.this.finish();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {

            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });
    }

    private void initRefView() {
        mContent.setHasFixedSize(true);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(this));
        xRefreshView.setCustomFooterView(new XRefreshViewFooter(this));
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                page = 0;
                poi.getPOI("医院",
                        App.mShareP.getString(Values.LAST_LOCATION,""),
                        page, count);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                page++;
                isLoadMore = true;
                dialog = ProgressDialog.show(MoreHospitalActivity.this,
                        "", "加载更多...");
                poi.getPOI("医院",
                        App.mShareP.getString(Values.LAST_LOCATION,""),
                        page, count);
            }
        });
    }



    @Override
    public void onLoad(List<HospitalBean> data) {

        if (isLoadMore){
            isLoadMore = false;
            dialog.dismiss();
            xRefreshView.stopLoadMore();
            if (data.size() == 0) {
                SnackUtils.showSnack(mContent,"最后一页啦..");
                return ;
            }
            //加载更多的情况
            List<HospitalBean> old = mAdapter.getItemList();
            old.addAll(data);
            mAdapter.setItemList(old);
            mAdapter.notifyDataSetChanged();
        }else {
            xRefreshView.stopRefresh();
            mAdapter = new SimpleRecycleAdapter(this
                    , new MoreHospitalAdapterOption(), data);
            mContent.setAdapter(mAdapter);
        }

    }
}
