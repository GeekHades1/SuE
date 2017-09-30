package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class MonthBodyCheckActivity extends BaseActivity {

    @BindView(R.id.my_title_bar)
    BGATitleBar mTitleBar;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,MonthBodyCheckActivity.class));

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_body_check;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {
        initBar();
    }
    private void initBar() {
        mTitleBar.setTitleText("每月体检");
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                back();
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

    private void back() {
        this.finish();
    }

    @Override
    public void initData() {

    }

}
