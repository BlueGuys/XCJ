package com.hongyan.xcj.core;


import android.util.Log;
import android.widget.Toast;

import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

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
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
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
                if (result != null && result.data != null) {
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(),"已取消收藏",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        request.addParam("aid", articleId);
        request.addParam("type", type);
        new JPBaseModel().sendRequest(request);
    }

}
