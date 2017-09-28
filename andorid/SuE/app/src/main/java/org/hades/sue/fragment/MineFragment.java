package org.hades.sue.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.activity.LoginActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.helper.LayoutMineModuleHelper;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by Hades on 2017/9/21.
 */

public class MineFragment extends BaseFragment implements LayoutMineModuleHelper.OnMineClickListener {

    private BGATitleBar mTitleBar;

    @BindView(R.id.layou_mine_user_information)
    View mViewUserInfo;
    @BindView(R.id.layout_mine_doctor)
    View mViewDoctor;
    @BindView(R.id.layout_mine_medical_record)
    View mViewMedicalRecord;
    @BindView(R.id.layout_mine_setting)
    View mViewSetting;

    private LayoutMineModuleHelper mMineDoctorHelper;
    private LayoutMineModuleHelper mMineMedicalRecorHelper;
    private LayoutMineModuleHelper mMineSettingHelper;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
        mTitleBar = (BGATitleBar) mHomeActivity.getTitleBar();
        initBar();
        initMine();
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
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (App.mShareP.getBoolean(Values.isLogin, true)) {
            enterLogin();
        }
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

    private void enterLogin() {
        LoginActivity.startActivity(getActivity());
    }
}
