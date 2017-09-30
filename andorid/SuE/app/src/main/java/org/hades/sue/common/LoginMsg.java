package org.hades.sue.common;

/**
 * Created by Hades on 2017/9/30.
 */

public class LoginMsg {
    public int state;
    public String msg;
    public String username;
    public String token;

    public LoginMsg(int state) {
        this.state = state;
    }
}
