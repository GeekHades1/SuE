package org.hades.sue.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

public class SplashActivity extends AppCompatActivity {

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
