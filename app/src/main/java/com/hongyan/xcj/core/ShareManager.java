package com.hongyan.xcj.core;


import android.app.Activity;
import android.widget.Toast;

import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareManager {

    private static volatile ShareManager instance;

    private ShareManager() {
    }

    public static ShareManager getInstance() {
        if (instance == null) {
            synchronized (ShareManager.class) {
                if (instance == null) {
                    instance = new ShareManager();
                }
            }
        }
        return instance;
    }

    public void share(Activity activity, String articleId, String type) {
        if (null == articleId) {
            return;
        }
        JPRequest request = new JPRequest<>(ShareResult.class, UrlConst.getShareUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                ShareResult result = (ShareResult) response.getResult();
                if (result != null && result.data != null && result.data.share_info != null) {
                    showShare(activity, result.data.share_info);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "网络繁忙", Toast.LENGTH_SHORT).show();
            }
        });
        request.addParam("id", articleId);
        request.addParam("type", type);//0资讯1研究报告
        new JPBaseModel().sendRequest(request);
    }

    private void showShare(Activity activity, ShareResult.ShareInfo info) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(info.title);
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(info.url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(info.note);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(info.photo);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(info.url);
        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(activity);
    }
}
