package org.hades.sue.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.activity.LoginActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.common.LoginMsg;

import butterknife.BindView;

/**
 * Created by Hades on 2017/10/22.
 */

public class LoginCheckPhoneFragment extends BaseFragment
        implements View.OnClickListener{

    @BindView(R.id.login_btn)
    TextView mNextBtn;

    @BindView(R.id.et_login_phone)
    EditText mPhoneEt;

    @BindView(R.id.tv_register)
    TextView  mResTv;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_check_phone;
    }

    @Override
    public void initViews() {
        mNextBtn.setOnClickListener(this);
        mResTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        LoginMsg msg = new LoginMsg(1);
        switch (v.getId()) {
            case R.id.login_btn:
                msg.state = LoginActivity.NEXT_STEP_STATE;
                msg.username = mPhoneEt.getText().toString().trim();
            break;
            case R.id.tv_register:
                msg.state = LoginActivity.REGISTER_STATE;
                break;
        }
        EventBus.getDefault().post(msg);
    }
}
