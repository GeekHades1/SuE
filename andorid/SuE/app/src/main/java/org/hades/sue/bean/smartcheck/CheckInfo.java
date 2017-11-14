package org.hades.sue.bean.smartcheck;

import java.io.Serializable;

/**
 * Created by Hades on 2017/11/13.
 */

public class CheckInfo implements Serializable{
    public String sid;
    public String verifyCode;
    public String ts;

    @Override
    public String toString() {
        return "Info{" +
                "sid='" + sid + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}
