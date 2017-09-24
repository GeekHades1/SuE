package org.hades.sue.presenter.impl;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import org.hades.sue.R;
import org.hades.sue.presenter.IHomePresenter;

/**
 * Created by Hades on 2017/9/24.
 */

public class HomePresenter implements IHomePresenter {

    /**
     * 标准bar
     */
    private View mNormalBar = null;

    private View mBar = null;

    private RelativeLayout mBarContent = null;

    private Context mContext;

    @Override
    public void start(Context context) {
        mContext = context;
        mNormalBar =
                View.inflate(mContext, R.layout.title_bar_normal,null);
    }

    @Override
    public void setTitleBar( View bar,int nFlags) {
        if (mBar == null){
            mBar = bar;
            mBarContent = bar.findViewById(R.id.rl_title_content);
        }
        switch (nFlags){
            case BAR_NORMAL:
                setNormalBar();
                break;
        }
    }

    private void reLayout(){
        mBarContent.removeAllViews();
    }

    private void setNormalBar() {
        reLayout();
        mBarContent.addView(mNormalBar);
    }
}
