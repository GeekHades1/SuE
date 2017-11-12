package org.hades.sue.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.adapter.ExBodyCheckAdapter;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.bean.BodyCheck;
import org.hades.sue.bean.RData;
import org.hades.sue.utils.Logger;
import org.hades.sue.utils.SnackUtils;
import org.hades.sue.view.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BeforeBodyCheckActivity extends BaseActivity {

    public static final String TAG = BeforeBodyCheckActivity.class.getSimpleName();

    @BindView(R.id.my_title_bar)
    BGATitleBar mTitleBar;

    @BindView(R.id.id_tablayout)
    MyTabLayout mTablayout;

    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;


    private List<List<BodyCheck>>  cacheList;

    private ExBodyCheckAdapter mAdapter;

    private ProgressDialog dialog ;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,BeforeBodyCheckActivity.class));
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_before_body_check;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {
        initBar();
    }

    private void initContent() {
        App.mSueService.getBodyList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<List<BodyCheck>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RData<List<BodyCheck>> data) {
                        //Logger.d(TAG,data.data.toString());
                        convertBodyList(data.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        SnackUtils.showSnack(mViewPager,"网络异常!");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 将data转换成合适的data
     * @param data
     */
    private void convertBodyList(final List<BodyCheck> data) {
        Observable.create(new ObservableOnSubscribe<List<List<BodyCheck>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<BodyCheck>>> e) throws Exception {
                List<List<BodyCheck>> result = new ArrayList<>();
                String flag = "-1";
                ArrayList<BodyCheck> sub = null;
                for (BodyCheck item : data) {
                    if (!item.bodyId.equals(flag)) {
                        //如果发现不等，说明遇到新类型
                        flag = item.bodyId;
                        if (sub != null) {
                            //如果以前加载过
                            result.add(sub);
                            sub = null;
                            sub = new ArrayList<>();
                        } else {
                            sub = new ArrayList<>();
                        }
                    }
                    sub.add(item);
                }
                Logger.d(TAG,"size = "+result.size());
                e.onNext(result);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<BodyCheck>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<BodyCheck>> lists) {
                        dialog.dismiss();
                        //Logger.d(TAG,lists.get(0).toString());
                        cacheList = lists; //保留一份缓存
                        mAdapter = new ExBodyCheckAdapter(getSupportFragmentManager(),
                                lists);
                        mViewPager.setAdapter(mAdapter);
                        mTablayout.setupWithViewPager(mViewPager);
                        Logger.d(TAG,"table count = "+mTablayout.getTabCount());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initBar() {
        mTitleBar.setTitleText("病前确诊");
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                back();
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

    private void back() {
        this.finish();
        overridePendingTransition(0,R.anim.out_scale);
    }

    @Override
    public void initData() {
        dialog = ProgressDialog.show(this,
                "", "加载中...");
        initContent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * fragment点击响事件
     * @param point
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTagClickBack(Point point){
        BodyCheck select = cacheList.get(point.x)
                .get(point.y);
        new AlertDialog.Builder(this)
                .setMessage("你选择的身体表现症状是：" + select.symptom + "\n确定开始诊断吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}
