package org.hades.sue.common;

/**
 * Created by Hades on 2017/9/30.
 */

public class LoginMsg {
    public int state;
    public String msg;
    public String username;
    public String token;
    public String passwordMD5;
    public String verifiCode;

    public LoginMsg(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "LoginMsg{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", passwordMD5='" + passwordMD5 + '\'' +
                ", verifiCode='" + verifiCode + '\'' +
                '}';
    }
}
