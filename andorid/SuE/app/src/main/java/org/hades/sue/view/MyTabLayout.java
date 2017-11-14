package org.hades.sue.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pools;
import android.util.AttributeSet;

/**
 * Created by Hades on 2017/11/11.
 */

public class MyTabLayout extends TabLayout {

    private static final Pools.Pool<Tab> sTabPool = new Pools.SynchronizedPool<>(20);

    public MyTabLayout(Context context) {
        super(context);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public Tab newTab() {
        sTabPool.toString();
        return super.newTab();
    }
}
