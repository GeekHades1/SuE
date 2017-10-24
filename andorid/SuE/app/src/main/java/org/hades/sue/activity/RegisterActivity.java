package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.RData;
import org.hades.sue.bean.RespoBean;
import org.hades.sue.common.UserMsg;
import org.hades.sue.fragment.RegisterCheckOtherFragment;
import org.hades.sue.fragment.RegisterCheckPhoneFragment;
import org.hades.sue.presenter.IRegisterPresenter;
import org.hades.sue.presenter.impl.RegisterPresenter;
import org.hades.sue.utils.ToastUtils;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity<IRegisterPresenter> {

    public static final String TAG = RegisterActivity.class.getSimpleName();


    public static final int NEXT_STEP_STATE = 1;
    public static final int REGISTER_STATE = 2;


    @BindView(R.id.my_title_bar_login)
    BGATitleBar mTitleBar;

    public static String NUMBER = "";

    IRegisterPresenter mPresenter;

    UserMsg mPostMsg;

    private BaseFragment fragments[] = new BaseFragment[2];

    RegisterCheckPhoneFragment phoneFragment = null;
    RegisterCheckOtherFragment otherFragment = null;

    private int mCurPage = 0;
    private int mPrePage = -1;



    @Override
    public void setPresenter(IRegisterPresenter presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {
        initBar();
        setDefaultView();
    }

    private void setDefaultView() {
        mCurPage = 0;
        changeFragment();
    }

    private void initBar() {
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                if (mCurPage != 1){
                    closeThis();
                }else {
                    mPrePage = mCurPage;
                    mCurPage = 0;
                    changeFragment();
                }
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

    private void closeThis() {
        this.finish();
    }

    @Override
    public void initData() {
        mPresenter = new RegisterPresenter();
        mPostMsg = new UserMsg(200);
    }

    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterEvent(UserMsg msg) {
        switch (msg.state) {
            case NEXT_STEP_STATE:
                isHasUser(msg);
                break;
            case REGISTER_STATE:
                //点击注册按钮
                Log.d(TAG,"注册！！！！");
                mPostMsg.passwordMD5 = msg.passwordMD5;
                mPostMsg.verifiCode = msg.verifiCode;
                doRegister();
                break;
        }
    }

    private void doNextStep(UserMsg msg) {
        if (mPresenter.checkPhone(msg.username)) {
                mPrePage = mCurPage;
                mCurPage = msg.state;
                mPostMsg.username = msg.username;
                NUMBER = msg.username;
                changeFragment();
        }else {
            ToastUtils.showShort(this,"输入手机号有误");
        }
    }

    private void isHasUser(final UserMsg msg) {
        //TODO: 增加申请账号存在检测
        App.mSueService.checkPhone(msg.username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<RespoBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RData<RespoBean> data) {
                        if (data.data.state){
                            //已被注册
                            ToastUtils.showShort(App.mContext,
                                    "该手机号码已被注册！");
                        }else {
                            mPostMsg.username = msg.username;
                            doNextStep(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void doRegister() {
        App.mSueService.register(mPostMsg.username,mPostMsg.passwordMD5,
                mPostMsg.verifiCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<RespoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,mPostMsg.toString());
                    }

                    @Override
                    public void onNext(RData<RespoBean> data) {
                        ToastUtils.showShort(App.mContext,data.data.msg);
                        if(data.data.state){
                            //注册成功
                            closeThis();
                        }else {
                            //注册失败
                        }
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



    private void changeFragment() {
        //Log.d(TAG, "page - " + mCurPage);
        FragmentTransaction ft = mFManager.beginTransaction();
        switch (mCurPage) {
            case 0:
                if (phoneFragment == null) {
                    phoneFragment = new RegisterCheckPhoneFragment();
                    ft.add(R.id.fl_container, phoneFragment,
                            RegisterCheckPhoneFragment.class.getSimpleName());
                    fragments[mCurPage] = phoneFragment;
                }
                break;
            case 1:
                if (otherFragment == null) {
                    otherFragment = new RegisterCheckOtherFragment();
                    ft.add(R.id.fl_container, otherFragment,
                            RegisterCheckOtherFragment.class.getSimpleName());
                    fragments[mCurPage] = otherFragment;
                }
                break;
        }
        showFragment(ft, mCurPage);
        ft.commit();
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction ft) {
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                ft.hide(fragments[i]);
            }
        }
    }

    /**
     * 显示指定pos Fragment
     *
     * @param pos
     */
    private void showFragment(FragmentTransaction ft, int pos) {
        hideAllFragment(ft);
        boolean isNext = false;
        if (mPrePage - mCurPage < 0) {
            isNext = true;
        } else {
            isNext = false;
        }
        if (isNext) {
            ft.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        } else {
            ft.setCustomAnimations(R.anim.slide_out_left,
                    R.anim.slide_in_right);
        }
        ft.show(fragments[pos]);
    }
}
