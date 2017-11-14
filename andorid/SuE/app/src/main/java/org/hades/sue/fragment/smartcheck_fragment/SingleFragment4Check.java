package org.hades.sue.fragment.smartcheck_fragment;

import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.smartcheck.BaseAnswer;
import org.hades.sue.bean.smartcheck.Question;

import butterknife.BindView;

/**
 * Created by Hades on 2017/11/13.
 *
 * 单选题
 */

public class SingleFragment4Check extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.id_question_title)
    TextView mQuestionTitle;

    @BindView(R.id.yes_btn)
    TextView yes_btn;

    @BindView(R.id.no_btn)
    TextView no_btn;

    private BaseAnswer response;

    private Question singleQuestion;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_single_select_smartcheck;
    }

    @Override
    public void initViews() {
        mQuestionTitle.setText("(单选题) "+singleQuestion.questionText);
        yes_btn.setText(singleQuestion.answers.get(0).answerText);
        no_btn.setText(singleQuestion.answers.get(1).answerText);

        yes_btn.setOnClickListener(this);
        no_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        response = new BaseAnswer();
    }

    @Override
    public void onClick(View v) {
        response.questionId =
                singleQuestion.questionId;
        switch (v.getId()){
            case R.id.yes_btn:
                //设置Id
                response.answer = singleQuestion.answers.get(0)
                        .answerId + "";
                break;
            case R.id.no_btn:
                response.answer = singleQuestion.answers.get(1)
                        .answerId + "";
                break;
        }
        //呼叫Activity
        EventBus.getDefault().post(response);
    }

    public void setQuestion(Question single){
        singleQuestion = single;
    }
}
