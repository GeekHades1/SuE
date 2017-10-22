package org.hades.sue.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import org.hades.sue.common.UserMsg;
import org.hades.sue.presenter.ILoginPresenter;

/**
 * Created by Hades on 2017/9/30.
 */

public class LoginPresenter implements ILoginPresenter {

    String regexPhone = "^[1][3,4,5,7,8][0-9]{9}$";

    @Override
    public void start(Context context) {

    }

    @Override
    public UserMsg login(String username, String psw) {
        UserMsg msg = new UserMsg(0);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(psw)) {
            msg.msg = "账号或密码不能为空";
            msg.state = -1;
            return msg;
        }
        msg.msg = "验证成功！";
        return msg;
    }

    /**
     * 验证手机号是否符合规则
     * @param phone
     * @return
     */
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
}
