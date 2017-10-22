package org.hades.sue.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hades.sue.activity.HomeActivity;

import butterknife.ButterKnife;

/**
 * Created by Hades on 2017/9/21.
 */

public abstract class BaseFragment extends Fragment {

    public abstract @LayoutRes int getLayoutId();

    public abstract void initViews();

    public abstract void initData();

    protected View      mContent;

    protected HomeActivity  mHomeActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContent = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this,mContent);
        initViews();
        initData();
        return mContent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity){
            mHomeActivity = (HomeActivity)context;
        }
    }
}
