package org.hades.sue.fragment.smartcheck_fragment;

import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.smartcheck.BodyTable;
import org.hades.sue.bean.smartcheck.Question;
import org.hades.sue.bean.smartcheck.QuestionHelper;

import butterknife.BindView;

/**
 * Created by Hades on 2017/11/14.
 */

public class ConclusionFragment4Check extends BaseFragment
        implements View.OnClickListener {

    @BindView(R.id.commit_btn)
    TextView mCommitBtn;

    @BindView(R.id.item_conclusion_title)
    TextView mTitle;

    @BindView(R.id.item_conclusion_content)
    TextView mContent;

    @BindView(R.id.item_conclusion_depart)
    TextView mDepart;

    @BindView(R.id.item_conclusion_action)
    TextView mAction;

    @BindView(R.id.item_conclusion_suggest)
    TextView mSuggest;

    @BindView(R.id.item_conclusion_emerge)
    TextView mEmerge;

    private Question conclusionQuestion;

    private BodyTable bodyTable;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_conclusion_smart_check;
    }

    @Override
    public void initViews() {
        QuestionHelper.Conclusion conclusion = conclusionQuestion.conclusions.get(0);
        //初始化诊断结果
        mTitle.setText(conclusion.title);
        mDepart.setText(conclusion.departInfo);
        mContent.setText(conclusion.content);
        mAction.setText(conclusion.actionLabel);
        if (conclusion.suggets != null) {
            StringBuffer sgB = new StringBuffer();
            for (QuestionHelper.Suggets sugget : conclusion.suggets) {
                sgB.append(sugget.sugget);
            }
            mSuggest.setText(sgB.toString());
        }

        mEmerge.setText(conclusion.emergencyLevel);

        mCommitBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        bodyTable = new BodyTable();
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(bodyTable);
    }


    public void setQuestion(Question question) {
        conclusionQuestion = question;
    }
}
