package org.hades.sue.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import org.hades.sue.R;

import butterknife.ButterKnife;

/**
 * Created by Hades on 2017/9/21.
 *
 * base Activity
 */

public abstract class BaseActivity<T> extends AppCompatActivity {

    public abstract void setPresenter(T presenter);

    //fragment manager
    protected FragmentManager mFManager;

    private ImmersionBar mImmersionBar;

    /**
     * must impl layout id
     * @return
     */
    public abstract int getLayoutId();

    public abstract View getTitleBar();

    public abstract void initViews();

    public abstract void initData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .barColor(R.color.titleBar)
                .titleBarMarginTop(getTitleBar())
                .init();   //所有子类都将继承这些相同的属性
        mFManager = getSupportFragmentManager();
        init();
        initViews();
        initData();

    }

    public void init() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        destroy();
        super.onDestroy();
    }

    /**
     * 防止冲突的销毁函数
     */
    public void destroy(){
    }
}
