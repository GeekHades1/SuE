package org.hades.sue.fragment;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.activity.LoginActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.common.UserMsg;
import org.hades.sue.utils.MD5Utils;

import butterknife.BindView;

/**
 * Created by Hades on 2017/10/22.
 */

public class LoginCheckPswFragment extends BaseFragment implements
        View.OnClickListener{

    @BindView(R.id.login_btn)
    TextView mLoginBtn;

    @BindView(R.id.et_password)
    EditText mPswEt;
    @Override
    public int getLayoutId() {

        return R.layout.fragment_login_check_psw;
    }

    @Override
    public void initViews() {
        mLoginBtn.setOnClickListener(this);
    }



    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                UserMsg msg = new UserMsg(LoginActivity.LOGIN_STATE);
                msg.passwordMD5 = MD5Utils.encoder(mPswEt.getText().toString());
                hideSoftWindow(mPswEt);
                EventBus.getDefault().post(msg);
                break;
        }
    }

    private void hideSoftWindow(View view){
        InputMethodManager imm = (InputMethodManager)
                App.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }



}
