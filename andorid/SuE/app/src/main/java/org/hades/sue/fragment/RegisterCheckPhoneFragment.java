package org.hades.sue.fragment;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.activity.RegisterActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.common.UserMsg;

import butterknife.BindView;

/**
 * Created by Hades on 2017/10/22.
 */

public class RegisterCheckPhoneFragment extends BaseFragment implements
        View.OnClickListener{

    @BindView(R.id.register_number)
    EditText mPhoneEt;

    @BindView(R.id.login_btn)
    TextView mNextBtn;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_register_check_phone;
    }

    @Override
    public void initViews() {
        mNextBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        UserMsg msg = new UserMsg(200);
        switch (v.getId()) {
            case R.id.login_btn:
                msg.state = RegisterActivity.NEXT_STEP_STATE;
                hideSoftWindow(mPhoneEt);
                msg.username = mPhoneEt.getText().toString().trim();
                break;
        }
        EventBus.getDefault().post(msg);
    }

    private void hideSoftWindow(View view){
        InputMethodManager imm = (InputMethodManager)
                App.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}
