package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.common.UserMsg;
import org.hades.sue.fragment.LoginCheckPhoneFragment;
import org.hades.sue.fragment.LoginCheckPswFragment;
import org.hades.sue.presenter.ILoginPresenter;
import org.hades.sue.presenter.impl.LoginPresenter;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class LoginActivity extends BaseActivity<LoginPresenter> {

    public static final String TAG = LoginActivity.class.getSimpleName();

    public static final int NEXT_STEP_STATE = 1;
    public static final int LOGIN_STATE = 2;
    public static final int REGISTER_STATE = -1;

    @BindView(R.id.my_title_bar_login)
    BGATitleBar mTitleBar;

    private BaseFragment fragments[] = new BaseFragment[2];

    LoginCheckPhoneFragment  phoneFragment = null;
    LoginCheckPswFragment    pswFragment = null;

    private UserMsg mPostMsg = new UserMsg(200);



    private ILoginPresenter mPresenter;

    private boolean isLogin = false;

    private int mCurPage = 0;
    private int mPrePage = -1;

    @Override
    public void setPresenter(LoginPresenter presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
                    isLogin = false;
                    loginSuccessAndFinish();
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

    /**
     * 成功登陆记录状态
     */
    private void loginSuccessAndFinish() {
        App.mShareP.setBoolean(Values.isLogin,isLogin);
        if (isLogin){
            long curTime = System.currentTimeMillis();
            //设置7天过期
            App.mShareP.setLong(Values.LAST_LOGIN_TIME, curTime);
        }
        this.finish();
    }


    @Override
    public void initData() {
        mPresenter = new LoginPresenter();
    }

    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void changeFragment() {
        //Log.d(TAG, "page - " + mCurPage);
        FragmentTransaction ft = mFManager.beginTransaction();
        switch (mCurPage) {
            case 0:
                if (phoneFragment == null) {
                    phoneFragment = new LoginCheckPhoneFragment();
                    ft.add(R.id.fl_container, phoneFragment,
                            LoginCheckPhoneFragment.class.getSimpleName());
                    fragments[mCurPage] = phoneFragment;
                }
                break;
            case 1:
                if (pswFragment == null) {
                    pswFragment = new LoginCheckPswFragment();
                    ft.add(R.id.fl_container, pswFragment,
                            LoginCheckPswFragment.class.getSimpleName());
                    fragments[mCurPage] = pswFragment;
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginPageEvent(UserMsg msg){
        switch (msg.state){
            case NEXT_STEP_STATE:
                doNextStep(msg);
                break;
            case LOGIN_STATE:
                mPostMsg.passwordMD5 = msg.passwordMD5;
                if(doLogin()){
                    isLogin = true;
                    loginSuccessAndFinish();
                }else {
                    ToastUtils.showShort(this,"登录失败！");
                }
                break;
            case REGISTER_STATE:
                RegisterActivity.startActivity(this);
                break;
        }
    }

    private void doNextStep(UserMsg msg){
        if (mPresenter.checkPhone(msg.username)) {
            if(isHasUser()){
                mPrePage = mCurPage;
                mCurPage = msg.state;
                mPostMsg.username = msg.username;
                changeFragment();
            }else {
                ToastUtils.showShort(this,"该手机号码尚未注册！");
            }
        }else {
            ToastUtils.showShort(this,"输入手机号有误");
        }
    }

    private boolean isHasUser() {
        //TODO: 增加账号存在检测
        return true;
    }

    private boolean doLogin() {

        return false;
    }
}
