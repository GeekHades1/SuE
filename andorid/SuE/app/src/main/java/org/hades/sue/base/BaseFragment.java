package org.hades.sue.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hades on 2017/9/21.
 */

public abstract class BaseFragment extends Fragment {

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initData();

    protected View      mContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContent = inflater.inflate(getLayoutId(), null);
        initViews();
        initData();
        return mContent;
    }
}
