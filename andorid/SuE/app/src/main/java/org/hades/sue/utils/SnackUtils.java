package org.hades.sue.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Hades on 2017/11/9.
 */

public class SnackUtils {

    public static void showSnack(View view, CharSequence text) {
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show();
    }
}
