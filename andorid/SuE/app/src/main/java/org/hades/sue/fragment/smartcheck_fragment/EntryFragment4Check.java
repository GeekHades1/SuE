package org.hades.sue.fragment.smartcheck_fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.smartcheck.BaseAnswer;
import org.hades.sue.bean.smartcheck.Question;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Hades on 2017/11/14.
 */

public class EntryFragment4Check extends BaseFragment implements
        View.OnClickListener{

    @BindView(R.id.id_question_title)
    TextView mQuestionTitle;

    @BindView(R.id.ll_entry)
    LinearLayout ll_entryContainer;

    @BindView(R.id.commit_btn)
    TextView mCommitBtn;

    @BindView(R.id.item_label)
    TextView mLabel;

    @BindView(R.id.item_value)
    EditText mValue;

    private Question entryQuestion;

    private BaseAnswer baseAnswer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_entry_smart_check;
    }

    @Override
    public void initViews() {
        mQuestionTitle.setText("(填空题) "+entryQuestion.questionText);
        mLabel.setText(entryQuestion.entries.get(0).label);
        mValue.setHint(entryQuestion.entries.get(0).value);

        mCommitBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        baseAnswer = new BaseAnswer();
    }

    @Override
    public void onClick(View v) {
        baseAnswer.values = new ArrayList<>();
        BaseAnswer.EntryValues entryValues = new BaseAnswer.EntryValues();
        baseAnswer.questionId = entryQuestion.questionId;
        entryValues.key = entryQuestion.entries.get(0).key;
        String value = mValue.getText().toString().trim();
        entryValues.value = value.equals("") ?
                entryQuestion.entries.get(0).value :
                value;
        baseAnswer.values.add(entryValues);
        EventBus.getDefault().post(baseAnswer);
    }

    public void setQuestion(Question single){
        entryQuestion = single;
    }



}
