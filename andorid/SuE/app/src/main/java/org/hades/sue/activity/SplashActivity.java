package org.hades.sue.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

import static org.hades.sue.activity.SplashActivity.LOCATION_CODE;
import static org.hades.sue.activity.SplashActivity.PHONE_CODE;
import static org.hades.sue.activity.SplashActivity.STORAGE_CODE;

@PermissionsRequestSync(
        permission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},
        value = {LOCATION_CODE,
                STORAGE_CODE,
                PHONE_CODE})

public class SplashActivity extends AppCompatActivity {

    public static final int LOCATION_CODE = 1;
    public static final int STORAGE_CODE = 2;
    public static final int PHONE_CODE = 3;

    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    @BindView(R.id.rl_splash)
    RelativeLayout  mRlBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initPermission();
        if (App.mShareP.getBoolean(Values.isFirst,true)){
            setBannerListener();
            processLogic();
        }else {
            mRlBg.setBackgroundResource(R.drawable.sue);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enterHome();
                }
            },1500);
        }


    }

    private void initPermission() {
        Permissions4M.get(this)
                .requestForce(true)
                .requestSync();
    }

    @PermissionsDenied({LOCATION_CODE, STORAGE_CODE, PHONE_CODE})
    public void denied(int code) {
        switch (code) {
            case LOCATION_CODE:
                Log.d(TAG, "位置权限获取失败");
                break;
            case STORAGE_CODE:
                Log.d(TAG, "内存权限获取失败");
                break;
            case PHONE_CODE:
                Log.d(TAG, "位置权限获取失败");
                break;
            default:
                break;
        }
    }

    private void setBannerListener() {
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter,0,
            new BGABanner.GuideDelegate() {
                @Override
                public void onClickEnterOrSkip() {
                    App.mShareP.setBoolean(Values.isFirst,false);
                    enterHome();
                }
            });
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
}
