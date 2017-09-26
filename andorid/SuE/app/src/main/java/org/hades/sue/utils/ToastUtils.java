package org.hades.sue.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Hades on 2017/9/25.
 */

public class ToastUtils {
    private final static boolean isShow = true;

    private ToastUtils(){
        throw new UnsupportedOperationException("ToastUtils cannot be instantiated");
    }

    public static void showShort(Context context, CharSequence text) {
        if(isShow) {
            Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(Context context,CharSequence text) {
        if(isShow){
            Toast.makeText(context,text,Toast.LENGTH_LONG).show();
        }
    }
}
