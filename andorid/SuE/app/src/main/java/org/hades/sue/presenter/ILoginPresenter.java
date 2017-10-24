package org.hades.sue.presenter;

import org.hades.sue.base.BasePresenter;
import org.hades.sue.common.UserMsg;

/**
 * Created by Hades on 2017/9/30.
 */

public interface ILoginPresenter extends BasePresenter {
    UserMsg login(String username, String psw);
    boolean checkPhone(String phone);
}
