package org.hades.sue.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.smartcheck.BaseAnswer;
import org.hades.sue.bean.smartcheck.BodyTable;
import org.hades.sue.bean.smartcheck.CheckInfo;
import org.hades.sue.bean.smartcheck.Question;
import org.hades.sue.bean.smartcheck.QuestionHelper;
import org.hades.sue.bean.smartcheck.StartSmartCheck;
import org.hades.sue.common.SmartCheckService;
import org.hades.sue.fragment.smartcheck_fragment.ConclusionFragment4Check;
import org.hades.sue.fragment.smartcheck_fragment.EntryFragment4Check;
import org.hades.sue.fragment.smartcheck_fragment.MultiFragment4Check;
import org.hades.sue.fragment.smartcheck_fragment.SingleFragment4Check;
import org.hades.sue.utils.Logger;
import org.hades.sue.utils.SnackUtils;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartSmartCheckActivity extends BaseActivity {

    private static final String TAG = StartSmartCheckActivity.class.getSimpleName();

    public static final String DATA_KEY = "data";

    /**
     * 单选ID
     */
    private static final int SINGLE_ID = 1;

    /**
     * 多选ID
     */
    private static final int MULTI_ID = 2;

    /**
     * 问答ID
     */
    private static final int ENTRY_ID = 3;

    /**
     * 结论ID
     */
    private static final int CONCLU_ID = 4;

    @BindView(R.id.my_title_bar)
    BGATitleBar mTitleBar;

    @BindView(R.id.fl_container)
    FrameLayout mContainer;

    private ProgressDialog dialog;

    private SmartCheckService mService;


    //诊断的凭据
    private CheckInfo checkInfo;

    private BaseFragment oldFragment = null;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_smart_check;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {
        initBar();
    }

    private void initBar() {
        mTitleBar.setTitleText(getString(R.string.smart_check));
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                back();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {

            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });
    }

    private void back() {
        this.finish();
        overridePendingTransition(0, R.anim.out_scale);
    }

    @Override
    public void initData() {
        showLoadDialog();
        checkInfo = (CheckInfo)
                getIntent().getExtras().getSerializable(DATA_KEY);
        //初始化服务器请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SmartCheckService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(SmartCheckService.class);
        //获取第一条问题
        getFirstQuestion();
    }

    /**
     * 获取第一条问题
     */
    private void getFirstQuestion() {
        mService.getQuestion(checkInfo.sid, new BaseAnswer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StartSmartCheck<Question>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StartSmartCheck<Question> questionData) {
                        //Logger.d(TAG,questionData.toString());
                        Question question = questionData.result;
                        resolveData(question);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        SnackUtils.showSnack(mContainer, "网络连接失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 处理问题分流
     *
     * @param question
     */
    private void resolveData(Question question) {
        //更换标题
        mTitleBar.setTitleText(getFormatStepString(question.currentStep));
        if (question.nodeType.equals(QuestionHelper.NODETYPE_QUESTION)) {
            resolveQuestion(question);
        } else if (question.nodeType.equals(QuestionHelper.NODETYPE_CONCLUSION)) {
            resolveConclusion(question);
        }
    }

    /**
     * 处理结果类型
     *
     * @param question
     */
    private void resolveConclusion(Question question) {
        mTitleBar.setTitleText(getString(R.string.smart_check));
        replaceFragment(CONCLU_ID, question);
    }

    /**
     * 处理问题类型
     *
     * @param question
     */
    private void resolveQuestion(Question question) {
        if (question.questionType.equals(QuestionHelper.QUESTION_TYPE_SINGLE)) {
            replaceFragment(SINGLE_ID, question);
        } else if (question.questionType.equals(QuestionHelper.QUESTION_TYPE_MULTI)) {
            replaceFragment(MULTI_ID, question);
        } else if (question.questionType.equals(QuestionHelper.QUESTION_TYPE_ENTRY)) {
            replaceFragment(ENTRY_ID, question);
        }
    }


    /**
     * 启动
     *
     * @param context
     * @param data
     */
    public static void startActivity(Context context, CheckInfo data) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_KEY, data);
        intent.setClass(context, StartSmartCheckActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void showLoadDialog() {
        dialog = ProgressDialog.show(this,
                "", "加载中...");
    }

    private String getFormatStepString(int arg) {
        return String.format(getString(R.string.smart_check_step), arg);
    }


    private void replaceFragment(int fragmentId, Question question) {
        android.support.v4.app.FragmentTransaction ft = mFManager.beginTransaction();
        if (oldFragment != null) {
            ft.remove(oldFragment);
            oldFragment = null;
        }
        ft.commit();
        ft = mFManager.beginTransaction();
        switch (fragmentId) {
            case SINGLE_ID:
                SingleFragment4Check s = new SingleFragment4Check();
                s.setQuestion(question);
                oldFragment = s;
                ft.replace(R.id.fl_container, oldFragment, SINGLE_ID + "");
                break;
            case MULTI_ID:
                MultiFragment4Check m = new MultiFragment4Check();
                m.setQuestion(question);
                oldFragment = m;
                ft.replace(R.id.fl_container, oldFragment, MULTI_ID + "");
                break;
            case ENTRY_ID:
                EntryFragment4Check e = new EntryFragment4Check();
                e.setQuestion(question);
                oldFragment = e;
                ft.replace(R.id.fl_container, oldFragment, ENTRY_ID + "");
                break;
            case CONCLU_ID:
                //TODO:处理结果
                ConclusionFragment4Check c = new ConclusionFragment4Check();
                c.setQuestion(question);
                oldFragment = c;
                ft.replace(R.id.fl_container, oldFragment, CONCLU_ID + "");
                break;
        }
        ft.setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right);
        ft.commit();
        dialog.dismiss();
    }


    @Override
    protected void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onStart();
    }

    /**
     * 选择题的响应函数
     *
     * @param response
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SelectQuestionAction(BaseAnswer response) {
        Logger.d("Answer", response.toString());
        nextStep(response);
    }

    /**
     * 结果响应
     * @param bodyTable
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void conclusionAciton(BodyTable bodyTable) {
        saveAndExit(bodyTable);
    }

    /**
     * 保存诊断结果并退出
     * @param bodyTable
     */
    private void saveAndExit(BodyTable bodyTable) {
        //TODO:保存诊断结果并退出
        back();
    }

    /**
     * 回答问题后的下一步操作
     *
     * @param response
     */
    private void nextStep(final BaseAnswer response) {
        showLoadDialog();
        Logger.d("nextStep", response.toString());
        mService.getQuestion(checkInfo.sid, response)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StartSmartCheck<Question>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StartSmartCheck<Question> questionStartSmartCheck) {
                        Logger.d(TAG, questionStartSmartCheck.toString());
                        resolveData(questionStartSmartCheck.result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        SnackUtils.showSnack(mContainer, "网络连接失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }
}
