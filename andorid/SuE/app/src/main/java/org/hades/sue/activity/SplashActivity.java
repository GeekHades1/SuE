package org.hades.sue.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.utils.Values;
import org.hades.sue.view.RotatePageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

import static org.hades.sue.activity.SplashActivity.FINE_LOCATION;
import static org.hades.sue.activity.SplashActivity.LOCATION_CODE;
import static org.hades.sue.activity.SplashActivity.MOUNT_UNMOUNT_FILESYSTEMS;
import static org.hades.sue.activity.SplashActivity.PHONE_CODE;
import static org.hades.sue.activity.SplashActivity.STORAGE_CODE;
import static org.hades.sue.activity.SplashActivity.WAKE_LOCK;
import static org.hades.sue.activity.SplashActivity.WRITE_SETTINGS;

@PermissionsRequestSync(
        permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                },
        value = {
                FINE_LOCATION,
                LOCATION_CODE,
                STORAGE_CODE,
                PHONE_CODE,
                WRITE_SETTINGS,
                WAKE_LOCK,
                MOUNT_UNMOUNT_FILESYSTEMS,

        })

public class SplashActivity extends AppCompatActivity {

    public static final int FINE_LOCATION = 0;
    public static final int LOCATION_CODE = 1;
    public static final int STORAGE_CODE = 2;
    public static final int PHONE_CODE = 3;
    public static final int WRITE_SETTINGS = 4;
    public static final int WAKE_LOCK = 5;
    public static final int MOUNT_UNMOUNT_FILESYSTEMS = 6;



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
        initPermission();
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

    private void initPermission() {
        Permissions4M.get(this)
                .requestForce(true)
                .requestSync();
    }

    @PermissionsDenied({FINE_LOCATION, LOCATION_CODE,
            STORAGE_CODE, PHONE_CODE,WAKE_LOCK,
            WRITE_SETTINGS, MOUNT_UNMOUNT_FILESYSTEMS})
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
                    guide_enter.setVisibility(View.GONE);
                    noFirst();
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
