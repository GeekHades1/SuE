package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.utils.MessageEvent;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.my_title_bar_login)
    BGATitleBar mTitleBar;
    @BindView(R.id.register_user_login)
    TextView mReisterUser;

    @Override
    public void setPresenter(Object presenter) {

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
        mReisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterRegisgter();
            }
        });
    }

    private void enterRegisgter() {
        RegisterActivity.startActivity(this);
    }

    @Override
    public void initData() {

    }

    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("0"));
    }
}
