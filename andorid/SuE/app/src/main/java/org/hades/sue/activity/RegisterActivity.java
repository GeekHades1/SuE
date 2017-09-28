package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.my_title_bar_register)
    BGATitleBar mTitleBar;

    @Override
    public void setPresenter(Object presenter) {

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

    }

    @Override
    public void initData() {

    }

    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
        return true;
    }

}
