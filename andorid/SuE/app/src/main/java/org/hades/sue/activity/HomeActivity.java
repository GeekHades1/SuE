package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.fragment.HomeFragment;
import org.hades.sue.fragment.BodyCheckFragment;
import org.hades.sue.fragment.MineFragment;
import org.hades.sue.presenter.IHomePresenter;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class HomeActivity extends BaseActivity<IHomePresenter> implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    private final String TAG = HomeActivity.class.getSimpleName();

    private BaseFragment     fragments[] = new BaseFragment[3];

    private HomeFragment   mHomeFragment = null;

    private IHomePresenter presenter;

    private BodyCheckFragment test1 = null;
    private MineFragment test2 = null;

    private int           mCurPage = 0;

    @BindView(R.id.navigation)
    BottomNavigationView  mBottomBar;

    @BindView(R.id.my_title_bar)
    BGATitleBar mTitleBar;


    @Override
    public void setPresenter(IHomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }


    @Override
    public void initViews() {
        //设置默认页面
        mBottomBar.setSelectedItemId(R.id.action_home);
        mCurPage = 0;
        changeFragment();
        mBottomBar.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {

    }

    //start this activity
    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context,HomeActivity.class));
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Log.d(TAG,"click + "+item.getItemId());
        switch (item.getItemId()){
            case R.id.action_home:
                mCurPage = 0;
                break;
            case R.id.action_body_check:
                mCurPage = 1;
                break;
            case R.id.action_mine:
                mCurPage = 2;
                break;
        }
        changeFragment();
        return true;
    }


    /**
     * 改变Fragment
     */
    private void changeFragment(){
        //Log.d(TAG, "page - " + mCurPage);
        FragmentTransaction ft = mFManager.beginTransaction();
        switch (mCurPage){
            case 0:
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    ft.add(R.id.fm_content,mHomeFragment,
                            HomeFragment.class.getSimpleName());
                    fragments[mCurPage] = mHomeFragment;
                }
                break;
            case 1:
                if (test1 == null){
                    test1 = new BodyCheckFragment();
                    ft.add(R.id.fm_content,test1,
                            BodyCheckFragment.class.getSimpleName());
                    fragments[mCurPage] = test1;
                }
                break;
            case 2:
                if (test2 == null){
                    test2 = new MineFragment();
                    ft.add(R.id.fm_content,test2,
                            MineFragment.class.getSimpleName());
                    fragments[mCurPage] = test2;
                }
                break;
        }
        showFragment(ft,mCurPage);
        ft.commit();
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction ft){
        for(int i = 0; i < fragments.length;i++) {
            if (fragments[i] != null) {
                ft.hide(fragments[i]);
            }
        }
    }

    /**
     * 显示指定pos Fragment
     * @param pos
     */
    private void showFragment(FragmentTransaction ft,int pos){
        hideAllFragment(ft);
        ft.show(fragments[pos]);
    }



}
