package com.hongyan.xcj.modules.share;


import android.widget.Toast;

import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.core.LogUtils;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareManager {

    private static volatile ShareManager instance;

    private ShareResult.ShareInfo info;

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

    public void share(BaseActivity activity, String articleId, String type) {
        if (activity == null) {
            return;
        }
        activity.startLoading();
        LogUtils.e("ShareManager 开始分享");
        JPRequest request = new JPRequest<>(ShareResult.class, UrlConst.getShareUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                activity.cancelLoading();
                if (null == response || null == response.getResult()) {
                    return;
                }
                ShareResult result = (ShareResult) response.getResult();
                if (result != null && result.data != null && result.data.share_info != null) {
                    LogUtils.e("ShareManager 分享信息获取成功");
                    showShareDialog(activity, result.data.share_info);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.cancelLoading();
                LogUtils.e("ShareManager 分享信息获取失败");
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.addParam("id", articleId);
        request.addParam("type", type);//0资讯1研究报告
        new JPBaseModel().sendRequest(request);
    }

    private void showShareDialog(BaseActivity activity, ShareResult.ShareInfo info) {
        final ShareDialog dialog = new ShareDialog(activity);
        dialog.show();
        dialog.setOnShareClickListener(new ShareDialog.OnShareClickListener() {
            @Override
            public void onChannelSelect(int channelId) {
                handleShare(channelId);
            }
        });
    }

    private void handleShare(int channelId) {
        switch (channelId) {
            case 0:
                shareToWeibo();
                break;
            case 1:
                shareToWeChat();
                break;
            case 2:
                shareToWeChatMoment();
                break;
            case 3:
                shareToQQ();
                break;
            case 4:
                shareToQQZone();
                break;
        }
    }

    private void shareToWeibo() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(info.title);
        sp.setTitleUrl(info.note); // 标题的超链接
        sp.setText(info.note);
        sp.setImageUrl(info.photo);
//                sp.setSite("发布分享的网站名称");
//                sp.setSiteUrl("发布分享网站的地址");
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.SSOSetting(true);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        weibo.share(sp);// 执行图文分享
    }

    private void shareToWeChat() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(info.title);
        sp.setTitleUrl(info.note); // 标题的超链接
        sp.setText(info.note);
        sp.setImageUrl(info.photo);
//                sp.setSite("发布分享的网站名称");
//                sp.setSiteUrl("发布分享网站的地址");
        Platform weibo = ShareSDK.getPlatform(Wechat.NAME);
        weibo.SSOSetting(true);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        weibo.share(sp);// 执行图文分享
    }

    private void shareToWeChatMoment() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(info.title);
        sp.setTitleUrl(info.note); // 标题的超链接
        sp.setText(info.note);
        sp.setImageUrl(info.photo);
//                sp.setSite("发布分享的网站名称");
//                sp.setSiteUrl("发布分享网站的地址");
        Platform weibo = ShareSDK.getPlatform(WechatMoments.NAME);
        weibo.SSOSetting(true);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        weibo.share(sp);// 执行图文分享
    }

    private void shareToQQ() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(info.title);
        sp.setTitleUrl(info.note); // 标题的超链接
        sp.setText(info.note);
        sp.setImageUrl(info.photo);
//                sp.setSite("发布分享的网站名称");
//                sp.setSiteUrl("发布分享网站的地址");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        qq.share(sp);// 执行图文分享
    }

    private void shareToQQZone() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(info.title);
        sp.setTitleUrl(info.note); // 标题的超链接
        sp.setText(info.note);
        sp.setImageUrl(info.photo);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        qzone.share(sp);// 执行图文分享
    }

}
