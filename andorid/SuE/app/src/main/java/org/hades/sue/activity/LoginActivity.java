package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.common.LoginMsg;
import org.hades.sue.presenter.impl.ILoginPresenter;
import org.hades.sue.presenter.impl.LoginPresenter;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class LoginActivity extends BaseActivity<LoginPresenter> {

    @BindView(R.id.my_title_bar_login)
    BGATitleBar mTitleBar;
    @BindView(R.id.register_user_login)
    TextView mRegisterUser;
    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.login_btn)
    View mBtLogin;

    private ILoginPresenter mPresenter;

    private boolean isLogin = false;

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
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(mEtUserName.getText().toString().trim()
                        ,mEtPassword.getText().toString().trim());
            }
        });
    }

    private void login(String username,String psw) {
        LoginMsg msg = mPresenter.login(username, psw);
    }

    private void initBar() {
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                isLogin = false;
                sendMsgAndFinish();
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
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterRegister();
            }
        });
    }

    /**
     * 更改登陆状态并且发送退出消息给HomeActivity
     */
    private void sendMsgAndFinish() {
        App.mShareP.setBoolean(Values.isLogin,isLogin);
        this.finish();
        //EventBus.getDefault().post(new LoginMsg(0));
    }

    private void enterRegister() {
        RegisterActivity.startActivity(this);
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
}
