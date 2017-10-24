package org.hades.sue.common;

/**
 * Created by Hades on 2017/9/30.
 */

public class UserMsg {
    public int state;
    public String msg;
    public String username;
    public String token;
    public String passwordMD5;
    public String verifiCode;

    public UserMsg(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", passwordMD5='" + passwordMD5 + '\'' +
                ", verifiCode='" + verifiCode + '\'' +
                '}';
    }
}
