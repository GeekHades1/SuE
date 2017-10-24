package org.hades.sue.common;

import org.hades.sue.bean.LoginBean;
import org.hades.sue.bean.RData;
import org.hades.sue.bean.RespoBean;
import org.hades.sue.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hades on 2017/10/22.
 */

public interface SueService {

    @FormUrlEncoded
    @POST("usr/checkphone")
    Observable<RData<RespoBean>> checkPhone(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("usr/sendVcode")
    Observable<RData<RespoBean>> sendVerifiCode(@Field("phone") String phone);

    /**
     * 注册接口
     * @param phone
     * @param psw
     * @param vcode
     * @return
     */
    @FormUrlEncoded
    @POST("usr/register")
    Observable<RData<RespoBean>> register(@Field("phone") String phone ,
                                          @Field("psw") String psw,
                                          @Field("vcode") String vcode);

    /**
     * 登陆注册
     * @param phone
     * @param psw
     * @return
     */
    @FormUrlEncoded
    @POST("usr/login")
    Observable<RData<LoginBean>> login(@Field("phone") String phone,
                                       @Field("password") String psw);

    @FormUrlEncoded
    @POST("usr/getUserInfo")
    Observable<RData<UserBean>> getUserInfo(@Field("phone") String phone);
}
