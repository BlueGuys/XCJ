package com.hongyan.xcj.core;

import android.app.Application;

import com.hongyan.xcj.network.RequestQueue;
import com.hongyan.xcj.network.toolbox.Volley;

/**
 * com.hongyan.base.BaseApplication
 */
public class BaseApplication extends Application {

    private static BaseApplication _instance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        this.mRequestQueue = Volley.newRequestQueue(this);
    }

    public static BaseApplication getInstance() {
        return _instance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
