package org.hades.sue.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import org.hades.sue.common.UserMsg;
import org.hades.sue.presenter.IRegisterPresenter;

/**
 * Created by Hades on 2017/10/22.
 */

public class RegisterPresenter implements IRegisterPresenter {

    String regexPhone = "^[1][3,4,5,7,8][0-9]{9}$";

    @Override
    public void start(Context context) {

    }

    @Override
    public boolean checkPhone(String phone) {
        try {
            if (!TextUtils.isEmpty(phone)) {
                //Log.d("testPhone", phone+" is ok? = "+phone.matches(regexPhone));
                return phone.matches(regexPhone);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean register(UserMsg msg) {
        return false;
    }
}
