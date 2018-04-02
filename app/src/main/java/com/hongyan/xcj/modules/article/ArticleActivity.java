package com.hongyan.xcj.modules.article;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseWebViewActivity;
import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.core.CollectionManager;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wangning on 2018/3/24.
 */

public class ArticleActivity extends BaseWebViewActivity {

    private boolean isCollection = false;

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
                    Uri uri = Uri.parse(mUrl);
                    String id = uri.getQueryParameter("id");
                    String type = uri.getQueryParameter("type");
                    if (isCollection) {
                        CollectionManager.getInstance().cancelCollectionArticle(id, StringUtils.isEmpty(type) ? "0" : type);
                        isCollection = false;
                    } else {
                        CollectionManager.getInstance().collectionArticle(id, StringUtils.isEmpty(type) ? "0" : type);
                        isCollection = true;
                    }
                    imageCollection.setImageResource(isCollection ? R.drawable.icon_cancel_collection : R.drawable.icon_collection);
                } catch (Exception e) {

                }
            }
        });
        mWebView.addJavascriptInterface(new ClientFunction(), "webView");
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



    public class ClientFunction {
        @JavascriptInterface
        public void setCollection(String str) {
            isCollection = "0".equals(str);
            imageCollection.setImageResource(isCollection ? R.drawable.icon_collection : R.drawable.icon_cancel_collection);
        }
    }
}
