package org.hades.sue.fragment.smartcheck_fragment;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.smartcheck.BaseAnswer;
import org.hades.sue.bean.smartcheck.Question;
import org.hades.sue.bean.smartcheck.QuestionHelper;
import org.hades.sue.utils.Logger;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Hades on 2017/11/14.
 *
 * 多选题
 */

public class MultiFragment4Check extends BaseFragment implements
        View.OnClickListener,CompoundButton.OnCheckedChangeListener{


    @BindView(R.id.id_question_title)
    TextView mQuestionTitle;

    @BindView(R.id.ll_check)
    LinearLayout ll_checkContainer;

    @BindView(R.id.commit_btn)
    TextView mCommitBtn;

    private Question multiQuestion;

    private ArrayList<Integer> selectIds;

    private BaseAnswer baseAnswer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_multi_smart_check;
    }

    @Override
    public void initViews() {
        mQuestionTitle.setText("(多选题) "+multiQuestion.questionText);
        for (QuestionHelper.Answer answer : multiQuestion.answers) {
            CheckBox item = getCheckBox();
            item.setText(answer.answerText);
            item.setTag(answer.answerId);
            ll_checkContainer.addView(item);
        }
        mCommitBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        selectIds = new ArrayList<>();
        baseAnswer = new BaseAnswer();
    }

    @Override
    public void onClick(View v) {
        baseAnswer.questionId = multiQuestion.questionId;
        String answers = selectIds.toString().replace("[", "");
        answers = answers.replace("]", "");
        baseAnswer.answers = answers;
        Logger.d("multi",answers);
        EventBus.getDefault().post(baseAnswer);
    }

    public void setQuestion(Question single){
        multiQuestion = single;
    }

    /**
     * 返回一个已经带监听的checkBox
     * @return
     */
    private CheckBox getCheckBox(){
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setOnCheckedChangeListener(this);
        return checkBox;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        int id = (int) buttonView.getTag();
        if (isChecked) {
            selectIds.add(id);
        } else {
            selectIds.remove((Integer) id);
        }
    }
}
