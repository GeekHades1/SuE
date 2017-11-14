package org.hades.sue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.hades.sue.R;
import org.hades.sue.bean.smartcheck.BodyCheck;

import java.util.List;

/**
 * Created by Hades on 2017/11/11.
 */

public class BodyListTagAdapter extends TagAdapter<BodyCheck> {

    private Context mContext;
    private TagFlowLayout mFlowLayout;

    public BodyListTagAdapter(List<BodyCheck> datas,Context context,
                              TagFlowLayout layout) {
        super(datas);
        mContext = context;
        mFlowLayout = layout;
    }



    @Override
    public View getView(FlowLayout parent, int position, BodyCheck bodyCheck) {
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                mFlowLayout, false);
        tv.setText(bodyCheck.symptom);
        return tv;
    }
}
