package org.hades.sue.fragment;

import android.graphics.Point;

import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.R;
import org.hades.sue.adapter.BodyListTagAdapter;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.smartcheck.BodyCheck;

import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by Hades on 2017/11/11.
 */

public class ExBodyCheckFragment extends BaseFragment
implements TagFlowLayout.OnSelectListener{

    List<BodyCheck> data;

    private int id;

    private BodyListTagAdapter mAdapter;

    @BindView(R.id.id_flowlayout)
    TagFlowLayout mContainer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ex_body_check;
    }

    @Override
    public void initViews() {
        mContainer.setOnSelectListener(this);
    }

    @Override
    public void initData() {
        mAdapter = new BodyListTagAdapter(data, getContext(), mContainer);
        mContainer.setAdapter(mAdapter);
    }

    public void setData(List<BodyCheck> data){
        this.data = data;
    }


    @Override
    public void onSelected(Set<Integer> selectPosSet) {
        int position = 0;
        for (int i : selectPosSet) {
            position = i;
            break;
        }
        Point point = new Point();
        point.x = id;
        point.y = position;
        EventBus.getDefault().post(point); //发送坐标回去Activity
    }

    public void setId(int id) {
        this.id = id;
    }
}
