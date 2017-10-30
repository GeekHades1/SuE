package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.fragment.BodyCheckFragment;
import org.hades.sue.fragment.HomeFragment;
import org.hades.sue.fragment.MineFragment;
import org.hades.sue.presenter.IHomePresenter;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class HomeActivity extends BaseActivity<IHomePresenter> implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        TencentLocationListener {

    private final String TAG = HomeActivity.class.getSimpleName();

    private BaseFragment fragments[] = new BaseFragment[3];

    private HomeFragment mHomeFragment = null;
    private BodyCheckFragment mBodyCheckFragment = null;
    private MineFragment mMineFragment = null;

    private IHomePresenter presenter;
    TencentLocationManager locationManager;


    private int mCurPage = 0;
    private int mPrePage = 0;

    public static final int MORE_HOSPITAL = 0x0;
    public static final int MORE_DOCTOR = 0x1;
    public static final int MORE_NEWS = 0x2;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomBar;

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
        mPrePage = -1;
        changeFragment();
        mBottomBar.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        initTencentLocation();
    }

    /**
     * 初始
     */
    private void initTencentLocation() {
        TencentLocationRequest request = TencentLocationRequest.create()
                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        locationManager = TencentLocationManager.getInstance(this);
        int error = locationManager.requestLocationUpdates(request, this);
        if (error == 0) {
            Log.d(TAG, "位置监听成功");
        }
    }


    //start this activity
    public static boolean startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Log.d(TAG,"click + "+item.getItemId());
        mPrePage = mCurPage;
        switch (item.getItemId()) {
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
        if ((mPrePage != mCurPage) ) {
            changeFragment();
        }
        return true;
    }


    /**
     * 改变Fragment
     */
    private void changeFragment() {
        //Log.d(TAG, "page - " + mCurPage);
        FragmentTransaction ft = mFManager.beginTransaction();
        switch (mCurPage) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    ft.add(R.id.fl_container, mHomeFragment,
                            HomeFragment.class.getSimpleName());
                    fragments[mCurPage] = mHomeFragment;
                }
                break;
            case 1:
                if (mBodyCheckFragment == null) {
                    mBodyCheckFragment = new BodyCheckFragment();
                    ft.add(R.id.fl_container, mBodyCheckFragment,
                            BodyCheckFragment.class.getSimpleName());
                    fragments[mCurPage] = mBodyCheckFragment;
                }
                break;
            case 2:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    ft.add(R.id.fl_container, mMineFragment,
                            MineFragment.class.getSimpleName());
                    fragments[mCurPage] = mMineFragment;
                }
                break;
        }
        showFragment(ft, mCurPage);
        ft.commit();
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction ft) {
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                ft.hide(fragments[i]);
            }
        }
    }

    /**
     * 显示指定pos Fragment
     *
     * @param pos
     */
    private void showFragment(FragmentTransaction ft, int pos) {
        hideAllFragment(ft);
        boolean isNext = false;
        if (mPrePage - mCurPage < 0) {
            isNext = true;
        } else {
            isNext = false;
        }
        if (isNext) {
            ft.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        } else {
            ft.setCustomAnimations(R.anim.slide_out_left,
                    R.anim.slide_in_right);
        }
        ft.show(fragments[pos]);
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (TencentLocation.ERROR_OK == error) {
            // 定位成功
            Log.d(TAG, "city = " + location.getCity());
            if (mCurPage == 0) {
                mTitleBar.setLeftText(location.getCity());
                locationManager.removeUpdates(this);
            }
        } else {
            // 定位失败
            Log.e(TAG, "定位失败");
            if (mCurPage == 0) {
                mTitleBar.setLeftText("未知");
                locationManager.removeUpdates(this);
            }
        }
    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {

    }

    public void stopLocation() {
        locationManager.removeUpdates(this);
    }

    /**
     * 更多按钮点击响事件
     * @param moreNum
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoreClick(Integer moreNum){
        Intent intent = new Intent();
        switch (moreNum) {
            case MORE_HOSPITAL:
                break;
            case MORE_NEWS:
                intent.setClass(this, MoreNewsActivity.class);
                break;
            case MORE_DOCTOR:
                break;
                default:
                    break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}

