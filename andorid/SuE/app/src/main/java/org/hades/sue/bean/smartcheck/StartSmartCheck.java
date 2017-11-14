package org.hades.sue.bean.smartcheck;

import java.io.Serializable;

/**
 * Created by Hades on 2017/11/13.
 * {
 * "status":"OK",
 * "result":
 * {
 * "sid":"14865316091334605",  // 预诊SessionID
 * "verifyCode":"3LRQD7",   // 预诊Session校验码
 * "ts":"1486531202168"      // 服务端时间戳
 * }
 * }
 */

public class StartSmartCheck<T> implements Serializable{
    public String status; //状态码
    public T result; //内置信息



    @Override
    public String toString() {
        return "StartSmartCheck{" +
                "status='" + status + '\'' +
                ", result=" + result +
                '}';
    }
}

