package org.hades.sue.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import org.hades.sue.common.LoginMsg;
import org.hades.sue.presenter.ILoginPresenter;

/**
 * Created by Hades on 2017/9/30.
 */

public class LoginPresenter implements ILoginPresenter {
    @Override
    public void start(Context context) {

    }

    @Override
    public LoginMsg login(String username, String psw) {
        LoginMsg msg = new LoginMsg(0);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(psw)) {
            msg.msg = "账号或密码不能为空";
            msg.state = -1;
            return msg;
        }
        msg.msg = "验证成功！";
        return msg;
    }
}
