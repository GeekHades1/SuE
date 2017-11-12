package org.hades.sue.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import org.hades.sue.R;
import org.hades.sue.activity.BeforeBodyCheckActivity;
import org.hades.sue.activity.MonthBodyCheckActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.view.BodyCheckModuleView;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by Hades on 2017/9/21.
 */

public class BodyCheckFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = BodyCheckFragment.class.getSimpleName();

    private BGATitleBar mTitleBar;

    @BindView(R.id.item_bodycheck_month)
    BodyCheckModuleView mMonthCheck;

    @BindView(R.id.item_bodycheck_before_sym)
    BodyCheckModuleView mBeforeSym;

    private final MyHandler myHandler = new MyHandler();


    @Override
    public int getLayoutId() {
        return R.layout.fragment_bodycheck;
    }

    @Override
    public void initViews() {
        mTitleBar = (BGATitleBar) mHomeActivity.getTitleBar();
        initBar();
        initMonthCheck();
        initBeforeSymCheck();
    }

    private void initBeforeSymCheck() {
        mBeforeSym.setTag("before");
        mBeforeSym.setMainText(getString(R.string.smart_check));
        mBeforeSym.setLeftIcon(R.drawable.before_bodycheck);
        mBeforeSym.setDetailsText("帮助您快速确定自己的病情，有目的的推荐您药物以及相关医生");
        mBeforeSym.setOnClickListener(this);
    }

    private void initMonthCheck() {
        mMonthCheck.setTag("month");
        mMonthCheck.setMainText("每月体检");
        mMonthCheck.setLeftIcon(R.drawable.month_bodycheck);
        mMonthCheck.setDetailsText("每月体检可以是自己更加了解自己的身体情况，推荐！");
        mMonthCheck.setOnClickListener(this);
    }

    private void initBar() {
        mTitleBar.setTitleText("体检");
        mTitleBar.getLeftCtv().setVisibility(View.GONE);
        mTitleBar.getRightCtv().setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            initBar();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View view) {
        mMonthCheck.setEnabled(false);
        mBeforeSym.setEnabled(false);
        mMonthCheck.setClickable(false);
        mBeforeSym.setClickable(false);
        if (view.getId() == R.id.item_bodycheck_month) {
            myHandler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            MonthBodyCheckActivity.startActivity(mHomeActivity);
                        }
                    }
            ,200);
        } else if (view.getId() == R.id.item_bodycheck_before_sym) {
            myHandler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            BeforeBodyCheckActivity.startActivity(mHomeActivity);
                        }
                    }
                    ,200);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMonthCheck.setEnabled(true);
        mBeforeSym.setEnabled(true);
        mMonthCheck.setClickable(true);
        mBeforeSym.setClickable(true);
    }

    private final class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
