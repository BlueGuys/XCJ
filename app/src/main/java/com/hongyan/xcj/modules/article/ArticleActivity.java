package com.hongyan.xcj.modules.article;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseWebViewActivity;
import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.core.CollectionManager;
import com.hongyan.xcj.core.LogUtils;
import com.hongyan.xcj.core.ShareManager;
import com.hongyan.xcj.modules.event.CollectMessageEvent;
import com.hongyan.xcj.modules.event.TokenMessageEvent;
import com.hongyan.xcj.modules.main.info.recommend.InfoRecommendResult;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wangning on 2018/3/24.
 */

public class ArticleActivity extends BaseWebViewActivity {

    private boolean isCollection = false;
    private String aid;
    private String type;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, ArticleActivity.class);
        if (url != null) {
            if (url.contains("?")) {
                url += ("&&token=" + AccountManager.getInstance().getToken());
            } else {
                url += ("?token=" + AccountManager.getInstance().getToken());
            }
        }
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isCollection) {
                        CollectionManager.getInstance().cancelCollectionArticle(aid, StringUtils.isEmpty(type) ? "0" : type);
                        isCollection = false;
                    } else {
                        CollectionManager.getInstance().collectionArticle(aid, StringUtils.isEmpty(type) ? "0" : type);
                        isCollection = true;
                    }
                    imageCollection.setImageResource(isCollection ? R.drawable.icon_cancel_collection : R.drawable.icon_collection);
                } catch (Exception e) {

                }
            }
        });
        mWebView.addJavascriptInterface(new ClientFunction(), "webView");
        if (!StringUtils.isEmpty(mUrl)) {
            Uri uri = Uri.parse(mUrl);
            aid = uri.getQueryParameter("id");
            type = uri.getQueryParameter("type");
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class ClientFunction {

        @JavascriptInterface
        public void setCollection(String str) {
            isCollection = "0".equals(str);
            imageCollection.setImageResource(isCollection ? R.drawable.icon_collection : R.drawable.icon_cancel_collection);
        }

        @JavascriptInterface
        public void finish() {
            ArticleActivity.this.finish();
        }

        @JavascriptInterface
        public void setToken(String token) {
            AccountManager.getInstance().setToken(token);
        }

        @JavascriptInterface
        public void getToken() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String token = AccountManager.getInstance().getToken();
                    mWebView.loadUrl("javascript:onTokenResult('" + token + "')");
                }
            });
        }

        @JavascriptInterface
        public void login() {
            if (!AccountManager.getInstance().isLogin()) {
                EventBus.getDefault().post(new TokenMessageEvent());
            }
        }

        @JavascriptInterface
        public void share() {
            LogUtils.e("ArticleActivity 开始分享");
            ShareManager.getInstance().share(ArticleActivity.this, aid, type);
        }

        @JavascriptInterface
        public void collection() {
            CollectionManager.getInstance().collectionArticle(aid, StringUtils.isEmpty(type) ? "0" : type);
        }

        @JavascriptInterface
        public void cancelCollection() {
            CollectionManager.getInstance().cancelCollectionArticle(aid, StringUtils.isEmpty(type) ? "0" : type);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void collectMessage(CollectMessageEvent message) {
        if (message == null) {
            return;
        }
        String id = message.getId();
        String action = message.getAction();
        String type = message.getType();
        if (!StringUtils.isEmpty(id) && id.equals(aid)) {
            if (!StringUtils.isEmpty(action) && "1".equals(action)) {//收藏
                isCollection = true;
                imageCollection.setImageResource(R.drawable.icon_cancel_collection);
            }
            if (!StringUtils.isEmpty(action) && "2".equals(action)) {//取消收藏
                isCollection = false;
                imageCollection.setImageResource(R.drawable.icon_collection);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isCollection) {
                        LogUtils.e("调用webview收藏");
                        mWebView.loadUrl("javascript:setCollect('')");
                    } else {
                        LogUtils.e("调用webview取消收藏");
                        mWebView.loadUrl("javascript:cancelCollect('')");
                    }
                }
            });
        }

    }
}
