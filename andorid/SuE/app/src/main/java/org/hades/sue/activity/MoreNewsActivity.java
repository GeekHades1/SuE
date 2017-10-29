package org.hades.sue.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;
import com.taro.headerrecycle.helper.RecycleViewOnClickHelper;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.adapter.MoreNewsAdapterOption;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.bean.HeathNews;
import org.hades.sue.bean.RData;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.view.SmileyHeaderView;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoreNewsActivity extends BaseActivity {

    private static final String TAG = MoreNewsActivity.class.getSimpleName();

    @BindView(R.id.my_title_bar_back)
    BGATitleBar mTitleBar;

    @BindView(R.id.xref_view)
    XRefreshView xRefreshView;

    @BindView(R.id.rv_news_content)
    RecyclerView mNewsContent;

    SimpleRecycleAdapter<HeathNews> mAdapter;
    RecycleViewOnClickHelper clickHelper;

    int page = 0;
    int count = 10; // 每次读取条数


    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_more_news;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {
        initBar();
        initRefView();
        clickHelper = new RecycleViewOnClickHelper(this);
        mNewsContent.setLayoutManager(new LinearLayoutManager(
                this, LinearLayout.VERTICAL,false));
        clickHelper.attachToRecycleView(mNewsContent);
        clickHelper.setOnItemClickListener(new RecycleViewOnClickHelper.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, RecyclerView.ViewHolder holder) {
                WebActivity.startActivity(MoreNewsActivity.this,
                        (HeathNews) mAdapter.getItem(position));
                overridePendingTransition(R.anim.enter,R.anim.out);
                return false;
            }
        });
    }

    private void initBar() {
        mTitleBar.setTitleText(R.string.titile_jkzx);
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                MoreNewsActivity.this.finish();
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
        mNewsContent.setHasFixedSize(true);
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
                App.mSueService.getHeathNews(page * count, count)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RData<List<HeathNews>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG,"onSubscribe 获取资讯数据");
                            }

                            @Override
                            public void onNext(RData<List<HeathNews>> listRData) {
                                mAdapter = new SimpleRecycleAdapter<HeathNews>(
                                        MoreNewsActivity.this,
                                        new MoreNewsAdapterOption(),
                                        listRData.data
                                );
                                mNewsContent.setAdapter(mAdapter);
                                page++;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRefreshView.stopRefresh();
                                    }
                                }, 1000);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                ToastUtils.showShort(App.mContext,"资讯获取失败！");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                App.mSueService.getHeathNews(page * count, count)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RData<List<HeathNews>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG,"onSubscribe 获取资讯数据");
                            }

                            @Override
                            public void onNext(RData<List<HeathNews>> listRData) {
                                if(listRData.data.size() > 0){
                                    List<HeathNews> newList = mAdapter.getItemList();
                                    newList.addAll(listRData.data);
                                    mAdapter.setItemList(newList);
                                    mAdapter.notifyDataSetChanged();
                                    page++;
                                    xRefreshView.stopLoadMore();
                                }else {
                                    ToastUtils.showShort(MoreNewsActivity.this,
                                            "没有更多了！");
                                    xRefreshView.stopLoadMore();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                ToastUtils.showShort(App.mContext,"资讯获取失败！");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    /**
     * 向服务器获取数据
     */
    private void getData() {
        App.mSueService.getHeathNews(page * count, count)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<List<HeathNews>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"onSubscribe 获取资讯数据");
                    }

                    @Override
                    public void onNext(RData<List<HeathNews>> listRData) {
                        mAdapter = new SimpleRecycleAdapter<HeathNews>(
                                MoreNewsActivity.this,
                                new MoreNewsAdapterOption(),
                                listRData.data
                        );
                        mNewsContent.setAdapter(mAdapter);
                        page++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showShort(App.mContext,"资讯获取失败！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
