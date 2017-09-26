package org.hades.sue.presenter;

import android.view.View;

import org.hades.sue.base.BasePresenter;

/**
 * Created by Hades on 2017/9/21.
 */

public interface IHomePresenter extends BasePresenter {

    int BAR_NORMAL = -100;

    /**
     * 将contentBar设置入Bar
     */
    void setTitleBar(View bar,int vFlags);

}
