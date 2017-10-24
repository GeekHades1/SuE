package org.hades.sue.presenter;

import org.hades.sue.base.BasePresenter;
import org.hades.sue.common.UserMsg;

/**
 * Created by Hades on 2017/10/22.
 */

public interface IRegisterPresenter extends BasePresenter {
    boolean checkPhone(String phone);
    boolean register(UserMsg msg);
}
