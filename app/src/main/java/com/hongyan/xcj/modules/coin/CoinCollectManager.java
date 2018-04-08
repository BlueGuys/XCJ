package com.hongyan.xcj.modules.coin;


import android.widget.Toast;

import com.hongyan.xcj.base.EmptyResult;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.core.Account;
import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.modules.event.ChartMessageEvent;
import com.hongyan.xcj.modules.event.CollectMessageEvent;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

import org.greenrobot.eventbus.EventBus;

public class CoinCollectManager {

    private static volatile CoinCollectManager instance;

    private CoinCollectManager() {
    }

    public static CoinCollectManager getInstance() {
        if (instance == null) {
            synchronized (CoinCollectManager.class) {
                if (instance == null) {
                    instance = new CoinCollectManager();
                }
            }
        }
        return instance;
    }

    public void collectCoin(String coinId) {
        if (null == coinId) {
            return;
        }
        JPRequest request = new JPRequest<>(EmptyResult.class, UrlConst.getCollectCoinUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                EmptyResult result = (EmptyResult) response.getResult();
                if (result == null) {
                    return;
                }
                if (result.isSuccessful()) {
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                    ChartMessageEvent messageEvent = new ChartMessageEvent();
                    messageEvent.setIsCollectSuccessful(1);
                    EventBus.getDefault().post(messageEvent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "网络繁忙", Toast.LENGTH_SHORT).show();
            }
        });
        request.addParam("did", coinId);
        request.setCheckLogin(true);
        new JPBaseModel().sendRequest(request);
    }

    public void cancelCollectCoin(String coinId) {
        if (null == coinId) {
            return;
        }
        JPRequest request = new JPRequest<>(EmptyResult.class, UrlConst.getCancelCollectCoinUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                EmptyResult result = (EmptyResult) response.getResult();
                if (result != null && result.isSuccessful()) {
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
                    ChartMessageEvent messageEvent = new ChartMessageEvent();
                    messageEvent.setIsCollectSuccessful(0);
                    EventBus.getDefault().post(messageEvent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "网络繁忙", Toast.LENGTH_SHORT).show();
            }
        });
        request.addParam("did", coinId);
        request.setCheckLogin(true);
        new JPBaseModel().sendRequest(request);
    }


}
