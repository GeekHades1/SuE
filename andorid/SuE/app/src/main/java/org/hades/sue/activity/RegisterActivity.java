package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.common.LoginMsg;
import org.hades.sue.presenter.IRegisterPresenter;
import org.hades.sue.presenter.impl.RegisterPresenter;
import org.hades.sue.utils.MD5Utils;
import org.hades.sue.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class RegisterActivity extends BaseActivity<IRegisterPresenter> implements View.OnClickListener{

    public static final String TAG = RegisterActivity.class.getSimpleName();

    private static final int COUNTDOWN = 10;

    @BindView(R.id.my_title_bar_login)
    BGATitleBar mTitleBar;

    @BindView(R.id.register_number)
    EditText mPhoneEt;

    @BindView(R.id.et_verifi_code)
    EditText mVerifiCodeEt;

    @BindView(R.id.tv_send_verifi_code)
    TextView mSendVerifiCodeTv;

    @BindView(R.id.register_password)
    EditText mPasswordEt;

    @BindView(R.id.register_confirm_password)
    EditText mConfirmPswEt;

    @BindView(R.id.register_btn)
    TextView mRegisterBtn;

    IRegisterPresenter mPresenter;

    Timer              mTimer;

    LoginMsg           mPostMsg;


    int                timeCounter = COUNTDOWN;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                mSendVerifiCodeTv.setText(String.format(
                        getString(R.string.after_send_verifi_code)
                        ,timeCounter
                ));
            }else if(msg.what == 1){
                //结束重置
                mTimer.cancel();
                timeCounter = COUNTDOWN;
                mSendVerifiCodeTv.setEnabled(true);
                mSendVerifiCodeTv.setText(getString(R.string.send_verifi_code));
            }
        }
    };

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
        mSendVerifiCodeTv.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    private void initBar() {
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                closeThis();
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
        mPostMsg = new LoginMsg(200);
    }

    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_verifi_code:
                sendVerificationCode();
                break;
            case R.id.register_btn:
                if(checkEmpty()){
                    if(checkPassword()){
                        doRegister();
                    }
                }

                break;
        }
    }

    private void doRegister() {
        ToastUtils.showShort(this,"注册！！！"+mPostMsg.toString());
    }

    private boolean checkEmpty() {
        String phone = mPhoneEt.getText().toString().trim();
        String firstPsw = mPasswordEt.getText().toString().trim();
        String confirm = mConfirmPswEt.getText().toString().trim();
        String verifiCode = mVerifiCodeEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(firstPsw) ||
                TextUtils.isEmpty(confirm) ||
                TextUtils.isEmpty(verifiCode) ){
            ToastUtils.showShort(this,"请输入正确参数");
            return false;
        }
        mPostMsg.verifiCode = verifiCode;
        return true;
    }

    private boolean checkPassword() {

        String firstPsw = mPasswordEt.getText().toString().trim();
        String confirm = mConfirmPswEt.getText().toString().trim();

        if (firstPsw.length() < 6) {
            ToastUtils.showShort(this,"密码长度须大于6位");
            return false;
        }
        if (!firstPsw.equals(confirm)){
            ToastUtils.showShort(this,"两次输入密码不一致");
            return false;
        }

        //填入密码
        mPostMsg.passwordMD5 = MD5Utils.encoder(firstPsw);
        return true;
    }

    private void sendVerificationCode() {
        if (mPresenter.checkPhone(mPhoneEt.getText().toString().trim())) {
            //合法手机号码
            mPostMsg.username = mPhoneEt.getText().toString().trim();
            mTimer = new Timer();
            mSendVerifiCodeTv.setEnabled(false);
            //设置一分钟后再次点击
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(timeCounter > 0){
                        timeCounter--;
                        mHandler.sendEmptyMessage(0);
                    }else {
                        mHandler.sendEmptyMessage(1);
                    }
                }
            },0,1000);
        }else {
            //非法
            ToastUtils.showShort(this,"请输入正确的手机号码");
        }
    }
}
