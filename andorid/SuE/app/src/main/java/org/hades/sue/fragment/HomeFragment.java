package org.hades.sue.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;

import org.hades.sue.R;
import org.hades.sue.adapter.HomeAdapter;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.view.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/9/21.
 */

public class HomeFragment extends BaseFragment {


    private XRefreshView xRefreshView;
    private RecyclerView mRVContent;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeAdapter                mAdapter;

    private boolean isBottom = false;
    private int mLoadCount = 0;
    int lastVisibleItem = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        xRefreshView = mContent.findViewById(R.id.xref_view);
        mRVContent = mContent.findViewById(R.id.rv_content);
        mRVContent.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRVContent.setLayoutManager(mLayoutManager);
        mRVContent.addItemDecoration(new
                LinearDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        initRefView();
    }

    private void initRefView() {
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(false);
        //adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mLoadCount++;
                        if (mLoadCount >= 3) {//模拟没有更多数据的情况
                            xRefreshView.setLoadComplete(true);
                        } else {
                            // 刷新完成必须调用此方法停止加载
                            xRefreshView.stopLoadMore(false);
                            //当数据加载失败 不需要隐藏footerview时，可以调用以下方法，传入false，不传默认为true
                            // 同时在Footerview的onStateFinish(boolean hideFooter)，可以在hideFooter为false时，显示数据加载失败的ui
//                            xRefreshView1.stopLoadMore(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void initData() {
        mAdapter = new HomeAdapter(getContext(),getData());
        mRVContent.setAdapter(mAdapter);
    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("测试数据--------"+i);
        }
        return data;
    }
}
