package org.hades.sue.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.activity.RegisterActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.RData;
import org.hades.sue.bean.RespoBean;
import org.hades.sue.common.UserMsg;
import org.hades.sue.utils.MD5Utils;
import org.hades.sue.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hades on 2017/10/22.
 */

public class RegisterCheckOtherFragment extends BaseFragment implements
        View.OnClickListener{

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

    @BindView(R.id.tv_register_phone)
    TextView mRegisPhoneTv;

    private static final int COUNTDOWN = 60;
    int timeCounter = COUNTDOWN;
    Timer mTimer;

    UserMsg     mPostMsg;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mSendVerifiCodeTv.setText(String.format(
                        getString(R.string.after_send_verifi_code)
                        , timeCounter
                ));
            } else if (msg.what == 1) {
                //结束重置
                mTimer.cancel();
                timeCounter = COUNTDOWN;
                mSendVerifiCodeTv.setEnabled(true);
                mSendVerifiCodeTv.setText(getString(R.string.send_verifi_code));
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_check_other;
    }

    @Override
    public void initViews() {
        mSendVerifiCodeTv.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mPostMsg = new UserMsg(200);
    }

    @Override
    public void onResume() {
        super.onResume();
        setPhoneText(RegisterActivity.NUMBER);
    }

    public void setPhoneText(String text) {
        mRegisPhoneTv.setText(String.format(
                App.mContext.getString(R.string.register_phone), text
        ));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_verifi_code:
                sendVerificationCode();
                hideSoftWindow(mSendVerifiCodeTv);
                break;
            case R.id.register_btn:
                if (checkEmpty()){
                    if (checkPassword()){
                        mPostMsg.state = RegisterActivity.REGISTER_STATE;
                        EventBus.getDefault().post(mPostMsg);
                    }
                }
                hideSoftWindow(mConfirmPswEt);
                break;
        }
    }

    private boolean checkEmpty() {
        String firstPsw = mPasswordEt.getText().toString().trim();
        String confirm = mConfirmPswEt.getText().toString().trim();
        String verifiCode = mVerifiCodeEt.getText().toString().trim();
        if (TextUtils.isEmpty(firstPsw) ||
                TextUtils.isEmpty(confirm) ||
                TextUtils.isEmpty(verifiCode) ){
            ToastUtils.showShort(App.mContext,"请输入正确参数");
            return false;
        }
        mPostMsg.verifiCode = verifiCode;
        return true;
    }

    private boolean checkPassword() {

        String firstPsw = mPasswordEt.getText().toString().trim();
        String confirm = mConfirmPswEt.getText().toString().trim();

        if (firstPsw.length() < 6) {
            ToastUtils.showShort(App.mContext,"密码长度须大于6位");
            return false;
        }
        if (!firstPsw.equals(confirm)){
            ToastUtils.showShort(App.mContext,"两次输入密码不一致");
            return false;
        }

        //填入密码
        mPostMsg.passwordMD5 = MD5Utils.encoder(firstPsw);
        return true;
    }

    private void sendVerificationCode() {
        App.mSueService.sendVerifiCode(RegisterActivity.NUMBER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<RData<RespoBean>>() {
                    @Override
                    public void onNext(RData<RespoBean> data) {
                        if (data.data.state){
                            ToastUtils.showShort(App.mContext, data.data.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mTimer = new Timer();
        mSendVerifiCodeTv.setEnabled(false);
        //设置一分钟后再次点击
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timeCounter > 0) {
                    timeCounter--;
                    mHandler.sendEmptyMessage(0);
                } else {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }, 0, 1000);
    }

    private void hideSoftWindow(View view){
        InputMethodManager imm = (InputMethodManager)
                App.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


}
