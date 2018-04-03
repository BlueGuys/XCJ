package com.hongyan.xcj.core;


import android.widget.Toast;

import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.event.CollectMessageEvent;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

import org.greenrobot.eventbus.EventBus;

public class CollectionManager {

    private static volatile CollectionManager instance;

    private CollectionManager() {
    }

    public static CollectionManager getInstance() {
        if (instance == null) {
            synchronized (CollectionManager.class) {
                if (instance == null) {
                    instance = new CollectionManager();
                }
            }
        }
        return instance;
    }

    public void collectionArticle(String articleId, String type) {
        if (null == articleId) {
            return;
        }
        JPRequest request = new JPRequest<>(Account.class, UrlConst.getCollectUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                Account result = (Account) response.getResult();
                if (result != null && result.isSuccessful()) {
                    post(articleId, type, "1");
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "网络繁忙", Toast.LENGTH_SHORT).show();
            }
        });
        request.addParam("aid", articleId);
        request.addParam("type", type);
        new JPBaseModel().sendRequest(request);
    }

    public void cancelCollectionArticle(String articleId, String type) {
        if (null == articleId) {
            return;
        }
        JPRequest request = new JPRequest<>(Account.class, UrlConst.getCancelCollectUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                Account result = (Account) response.getResult();
                if (result != null && result.isSuccessful()) {
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
                    post(articleId, type, "2");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "网络繁忙", Toast.LENGTH_SHORT).show();
            }
        });
        request.addParam("aid", articleId);
        request.addParam("type", type);
        new JPBaseModel().sendRequest(request);
    }

    /**
     * @param id
     * @param type
     * @param action 1收藏 2取消
     */
    private void post(String id, String type, String action) {
        CollectMessageEvent event = new CollectMessageEvent();
        event.setId(id);
        event.setType(type);
        event.setAction(action);
        EventBus.getDefault().post(event);
    }


}
