package org.hades.sue.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.activity.LoginActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.RData;
import org.hades.sue.bean.UserBean;
import org.hades.sue.helper.LayoutMineModuleHelper;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hades on 2017/9/21.
 */

public class MineFragment extends BaseFragment implements LayoutMineModuleHelper.OnMineClickListener {

    private static final String TAG = MineFragment.class.getSimpleName();

    private BGATitleBar mTitleBar;

    @BindView(R.id.layou_mine_user_information)
    View mViewUserInfo;
    @BindView(R.id.layout_mine_doctor)
    View mViewDoctor;
    @BindView(R.id.layout_mine_medical_record)
    View mViewMedicalRecord;
    @BindView(R.id.layout_mine_setting)
    View mViewSetting;
    @BindView(R.id.layout_mine_logout)
    View mViewLogout;
    @BindView(R.id.ll_item_user_op_block)
    View mUserOpBlock;
    @BindView(R.id.iv_item_user_info_head_icon)
    View mHeadIcon;
    @BindView(R.id.tv_item_user_info_login)
    View mLoginBtn;
    @BindView(R.id.tv_item_user_info_name)
    TextView mUserName;


    private LayoutMineModuleHelper mMineDoctorHelper;
    private LayoutMineModuleHelper mMineMedicalRecorHelper;
    private LayoutMineModuleHelper mMineSettingHelper;
    private LayoutMineModuleHelper mMineLogoutHelper;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
        mTitleBar = (BGATitleBar) mHomeActivity.getTitleBar();
        initBar();
        if (App.mShareP.getBoolean(Values.isLogin, false)) {
            loginView();
        } else {
            noLoginView();
        }
    }

    private void noLoginView() {
        mUserOpBlock.setVisibility(View.GONE);
        mUserName.setText(R.string.no_login);
        mLoginBtn.setVisibility(View.VISIBLE);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.startActivity(mHomeActivity);
            }
        });
    }

    private void loginView() {
        String phone = App.mShareP.getString(Values.loginPhone, "");
        Log.d(TAG, "post phone = " + phone);
        App.mSueService.getUserInfo(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RData<UserBean> data) {
                        Log.d(TAG, "nickname = " + data.data.nickname);
                        mUserOpBlock.setVisibility(View.VISIBLE);
                        mUserName.setText(data.data.nickname);
                        mLoginBtn.setVisibility(View.GONE);
                        initMine();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initMine() {
        //挂号预约管理
        mMineDoctorHelper = new LayoutMineModuleHelper(mViewDoctor);
        mMineDoctorHelper.setTitle("挂号预约管理");
        mMineDoctorHelper.setOnLinClickListener(this);

        //病历管理
        mMineMedicalRecorHelper = new LayoutMineModuleHelper(mViewMedicalRecord);
        mMineMedicalRecorHelper.setTitle("病历管理");
        mMineMedicalRecorHelper.setOnLinClickListener(this);

        //设置
        mMineSettingHelper = new LayoutMineModuleHelper(mViewSetting);
        mMineSettingHelper.setTitle("设置");
        mMineSettingHelper.setOnLinClickListener(this);

        mMineLogoutHelper = new LayoutMineModuleHelper(mViewLogout);
        mMineLogoutHelper.setTitle("退出");
        mMineLogoutHelper.setOnLinClickListener(this);
    }

    @Override
    public void initData() {

    }

    private void initBar() {
        mTitleBar.setTitleText("我的");
        mTitleBar.getLeftCtv().setVisibility(View.GONE);
        mTitleBar.getRightCtv().setVisibility(View.GONE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            initBar();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onLinClickListener(View view) {
        if (mMineDoctorHelper.isMineLinOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "挂号预约");
        } else if (mMineMedicalRecorHelper.isMineLinOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "病历");
        } else if (mMineSettingHelper.isMineLinOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "设置");
        }else if(mMineLogoutHelper.isMineLinOnClick(view)){
            //退出登录
            logout();
        }
    }

    private void logout() {
        App.mShareP.setBoolean(Values.isLogin, false);
        App.mShareP.remove(Values.LAST_LOGIN_TIME);
        noLoginView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.mShareP.getBoolean(Values.isLogin, false)) {
            loginView();
        } else {
            noLoginView();
        }
    }
}
