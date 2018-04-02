package com.hongyan.xcj.core;


import android.util.Log;
import android.widget.Toast;

import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.utils.GsonUtils;
import com.hongyan.xcj.utils.SharePreferenceManager;
import com.hongyan.xcj.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

public class AccountManager {

    private static volatile AccountManager instance;
    private AccountInfo mAccountInfo;
    private String token;

    private AccountManager() {
        init();
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            synchronized (AccountManager.class) {
                if (instance == null) {
                    instance = new AccountManager();
                }
            }
        }
        return instance;
    }

    /**
     * 从SD卡读取账户信息
     */
    public void init() {
        readAccountInfo();
    }

    public void setToken(String token) {
        this.token = token;
        getAccountInfoFromServer(token);
    }

    public String getToken() {
        return this.token;
    }

    public AccountInfo getAccountInfo() {
        return mAccountInfo;
    }

    public boolean isLogin() {
        return mAccountInfo != null;
    }

    public void logout() {
        SharePreferenceManager.getInstance().deleteStr("account");
        EventBus.getDefault().post(new AccountMessageEvent(false));
    }

    public void login() {
        logout();

    }

    /**
     * 从网络拉取账户信息
     */
    private void getAccountInfoFromServer(final String token) {
        if (null == token) {
            return;
        }
        JPRequest request = new JPRequest<>(Account.class, UrlConst.getAccountInfo(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                Account result = (Account) response.getResult();
                if (result != null && result.data != null) {
                    AccountInfo accountInfo = new AccountInfo();
                    accountInfo.setId(result.data.id);
                    accountInfo.setEmail(result.data.email);
                    accountInfo.setStatus(result.data.status);
                    accountInfo.setNickname(result.data.nickname);
                    accountInfo.setMobile(result.data.mobile);
                    accountInfo.setToken(token);
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                    mAccountInfo = accountInfo;
                    saveAccountInfo(GsonUtils.toJson(accountInfo));
                    EventBus.getDefault().post(new AccountMessageEvent(true));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        request.addParam("token", token);
        new JPBaseModel().sendRequest(request);
    }

    private void saveAccountInfo(String accountJson) {
        SharePreferenceManager.getInstance().putString("account", accountJson);
    }

    private void readAccountInfo() {
        String accountJson = SharePreferenceManager.getInstance().getString("account");
        Log.e("XCJ", "accountJson=" + accountJson);
        if (!StringUtils.isEmpty(accountJson)) {
            mAccountInfo = GsonUtils.gsonResolve(accountJson, AccountInfo.class);
            if (mAccountInfo != null) {
                this.token = mAccountInfo.getToken();
            }
        }
    }
}
