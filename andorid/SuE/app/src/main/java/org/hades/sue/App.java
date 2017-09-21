package org.hades.sue;

import android.app.Application;

import org.hades.sue.utils.SpUtils;

/**
 * Created by Hades on 2017/9/21.
 */

public class App extends Application {

    public static SpUtils  mShareP;

    @Override
    public void onCreate() {
        super.onCreate();
        mShareP = SpUtils.init(this);
    }
}
