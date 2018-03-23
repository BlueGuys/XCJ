package com.hongyan.xcj.base;


import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.network.RequestQueue;

/**
 * com.jp.base.JPBaseModel
 */
public class JPBaseModel {

    public JPBaseModel() {

    }

    public void sendRequest(JPRequest request) {
        RequestQueue requestQueue = BaseApplication.getInstance().getRequestQueue();
        if(requestQueue!=null){
            requestQueue.add(request);
        }
    }
}
