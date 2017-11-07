package org.hades.sue;

import android.app.Application;
import android.content.Context;

import com.amap.api.maps2d.MapsInitializer;

import org.hades.sue.common.SueService;
import org.hades.sue.utils.SpUtils;
import org.hades.sue.utils.Values;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hades on 2017/9/21.
 */

public class App extends Application {

    public static SpUtils  mShareP;

    public static SueService mSueService;

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mShareP = SpUtils.init(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Values.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mSueService = retrofit.create(SueService.class);
        MapsInitializer.setApiKey("12555006a348f112e3e1561880ad8b12");
    }


}
