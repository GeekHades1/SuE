package org.hades.sue.fragment;

import android.content.Context;
import android.os.Bundle;
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
import org.hades.sue.utils.SnackUtils;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.utils.Values;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hades on 2017/9/21.
 */

public class MineFragment extends BaseFragment
        implements LayoutMineModuleHelper.OnMineClickListener {

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
    CircleImageView mHeadIcon;
    @BindView(R.id.tv_item_user_info_login)
    View mLoginBtn;
    @BindView(R.id.tv_item_user_info_name)
    TextView mUserName;


    boolean isClick2Login = false;

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
        mUserName.setVisibility(View.GONE);
        mLoginBtn.setVisibility(View.VISIBLE);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.startActivity(mHomeActivity);
                //点击了登陆按钮登陆
                isClick2Login = true;
            }
        });
    }

    private void loginView() {
        String phone = App.mShareP.getString(Values.loginPhone, "");
        mHeadIcon.setVisibility(View.VISIBLE);
        App.mSueService.getUserInfo(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RData<UserBean> data) {
                        mUserOpBlock.setVisibility(View.VISIBLE);
                        mUserName.setVisibility(View.VISIBLE);
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
        mMineDoctorHelper.setIcon(R.drawable.item_mine_register_manager);
        mMineDoctorHelper.setOnLinClickListener(this);

        //病历管理
        mMineMedicalRecorHelper = new LayoutMineModuleHelper(mViewMedicalRecord);
        mMineMedicalRecorHelper.setIcon(R.drawable.item_mine_table_manager);
        mMineMedicalRecorHelper.setTitle("病历管理");
        mMineMedicalRecorHelper.setOnLinClickListener(this);

        //设置
        mMineSettingHelper = new LayoutMineModuleHelper(mViewSetting);
        mMineSettingHelper.setIcon(R.drawable.item_mine_setting);
        mMineSettingHelper.setTitle("设置");
        mMineSettingHelper.setOnLinClickListener(this);

        mMineLogoutHelper = new LayoutMineModuleHelper(mViewLogout);
        mMineLogoutHelper.setIcon(R.drawable.item_mine_logout);
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

    /**
     * 退出登录操作
     */
    private void logout() {
        SnackUtils.showSnack(mUserOpBlock,getContext().getString(R.string.logout_string));
        App.mShareP.setBoolean(Values.isLogin, false);
        App.mShareP.remove(Values.loginPhone);
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
            if (isClick2Login){
                //如果检测状态是登陆并且点击过登陆按钮
                isClick2Login =  false; //重置
                //打出欢迎语
                SnackUtils.showSnack(mUserOpBlock,getString(R.string.login_hello));
            }
            loginView();
        } else {
            noLoginView();
        }
    }
}
