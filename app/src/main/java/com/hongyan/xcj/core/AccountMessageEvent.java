package com.hongyan.xcj.core;

/**
 * Created by wangning on 2018/3/21.
 */

public class AccountMessageEvent {

    private boolean isLogin;

    public AccountMessageEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
