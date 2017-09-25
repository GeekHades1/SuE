package org.hades.sue;

import android.app.Application;
import android.content.Context;

import org.hades.sue.utils.SpUtils;

/**
 * Created by Hades on 2017/9/21.
 */

public class App extends Application {

    public static SpUtils  mShareP;

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mShareP = SpUtils.init(this);
    }


}
