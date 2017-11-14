package org.hades.sue.common;

import org.hades.sue.bean.smartcheck.BaseAnswer;
import org.hades.sue.bean.smartcheck.CheckInfo;
import org.hades.sue.bean.smartcheck.Question;
import org.hades.sue.bean.smartcheck.StartSmartCheck;
import org.hades.sue.utils.Values;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Hades on 2017/11/13.
 *
 * 智能确诊接口
 */

public interface SmartCheckService {

    /**
     * 基础链接地址
     */
    String BASE_URL = "http://mydoctor.market.alicloudapi.com";

    /**
     * 开始智能诊断
     * @param symptomId
     * @return
     */
    @Headers({"Authorization: APPCODE "+ Values.SMARTCHECK_APPKEY})
    @FormUrlEncoded
    @POST("/startSession")
    Observable<StartSmartCheck<CheckInfo>> startCheck(@Field("symptomId")
                                                   String symptomId);

    /**
     * 获取诊断问题
     * @param sid
     * @param body
     * @return
     */
    @Headers({"Authorization: APPCODE " + Values.SMARTCHECK_APPKEY})
    @POST("/fetchQuestion")
    Observable<StartSmartCheck<Question>> getQuestion(@Query("sid") String sid,
                                                      @Body BaseAnswer body);
}
