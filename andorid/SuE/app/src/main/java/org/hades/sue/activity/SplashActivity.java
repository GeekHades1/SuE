package org.hades.sue.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.utils.Values;
import org.hades.sue.view.RotatePageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class SplashActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks{


    private static final String[] PERMS = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS};
    private static final int PERMS_CODE = 0x0001;

    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    @BindView(R.id.rl_splash)
    RelativeLayout  mRlBg;

    @BindView(R.id.rpv_top)
    RotatePageView mRotateView;

    @BindView(R.id.btn_guide_enter)
    Button guide_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        requiresPermission();
        if (App.mShareP.getBoolean(Values.isFirst,true)){
            setBannerListener();
            processLogic();
        }else {
            noFirst();
        }


    }

    /**
     * 不是第一次进入
     */
    private void noFirst() {
        mBackgroundBanner.setVisibility(View.GONE);
        mRotateView.setVisibility(View.VISIBLE);
        mRotateView.setEndListener(new RotatePageView.OnAnimatorEndListener() {
            @Override
            public void animEnd() {
                enterHome();
            }
        });
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) mRotateView.getLayoutParams();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
//            Log.d(TAG, "height =" + height);
        int margin_top = (int)( height / 1.4f);
        params.setMargins(0,-margin_top,0,0);
        mRotateView.setLayoutParams(params);
        mRotateView.startAnim();//开始动画
        mRlBg.setBackgroundResource(R.drawable.sue);
    }


    private void setBannerListener() {
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter,0,
            new BGABanner.GuideDelegate() {
                @Override
                public void onClickEnterOrSkip() {
                    App.mShareP.setBoolean(Values.isFirst,false);
                    guide_enter.setVisibility(View.GONE);
                    noFirst();
                }
            });
    }

    @AfterPermissionGranted(PERMS_CODE)
    private void requiresPermission() {
        if (EasyPermissions.hasPermissions(this, PERMS)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.perms),
                    PERMS_CODE, PERMS);
        }
    }

    private void enterHome(){
        HomeActivity.startActivity(this);
        this.finish();
        overridePendingTransition(R.anim.stand,R.anim.splash);
    }

    private void processLogic() {
        // 设置数据源
        mBackgroundBanner.setData(R.drawable.guide1,
                R.drawable.guide2,
                R.drawable.guide3);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //添加权限回调
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //获取权限
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //拒绝权限
    }
}
