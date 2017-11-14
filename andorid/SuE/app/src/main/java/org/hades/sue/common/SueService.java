package org.hades.sue.common;

import org.hades.sue.bean.smartcheck.BodyCheck;
import org.hades.sue.bean.DoctorBean;
import org.hades.sue.bean.HeathNews;
import org.hades.sue.bean.LoginBean;
import org.hades.sue.bean.RData;
import org.hades.sue.bean.RespoBean;
import org.hades.sue.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Hades on 2017/10/22.
 */

public interface SueService {

    /**
     * 检查手机
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("usr/checkphone")
    Observable<RData<RespoBean>> checkPhone(@Field("phone") String phone);

    /**
     * 发送验证码
     * @param phone
     * @return
     */
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

    /**
     * 获取用户信息
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("usr/getUserInfo")
    Observable<RData<UserBean>> getUserInfo(@Field("phone") String phone);

    /**
     * 获取健康资讯
     * @param start 获取的第一个下标
     * @param count 获取的个数
     * @return 资讯数组
     */
    @FormUrlEncoded
    @POST("common/getNews")
    Observable<RData<List<HeathNews>>> getHeathNews(@Field("start") int start,
                                         @Field("count") int count);

    /**
     * 获取医生列表
     * @param start
     * @param count
     * @return
     */
    @FormUrlEncoded
    @POST("common/doctorInfo")
    Observable<RData<List<DoctorBean>>> getDoctorInfo(@Field("start") int start,
                                                      @Field("count") int count);

    /**
     * 获取症状列表
     * @return
     */
    @GET("check/bodylist")
    Observable<RData<List<BodyCheck>>> getBodyList();
}
