package com.hongyan.xcj.modules.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseWebViewActivity;
import com.hongyan.xcj.core.CollectionManager;

/**
 * Created by wangning on 2018/3/24.
 */

public class ArticleActivity extends BaseWebViewActivity {

    private boolean isCollection = false;
    private String articleId = "1111111111";

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, ArticleActivity.class);
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
                if (isCollection) {
                    CollectionManager.getInstance().cancelCollectionArticle(articleId);
                    isCollection = false;
                } else {
                    CollectionManager.getInstance().collectionArticle(articleId);
                    isCollection = true;
                }
                imageCollection.setImageResource(isCollection ? R.drawable.icon_cancel_collection : R.drawable.icon_collection);
            }
        });
        mWebView.addJavascriptInterface(new ClientFunction(), "webView");
    }

    public class ClientFunction {
        @JavascriptInterface
        public void collection(String str) {
            isCollection = "0".equals(str);
            imageCollection.setImageResource(isCollection ? R.drawable.icon_collection : R.drawable.icon_cancel_collection);
        }

        @JavascriptInterface
        public void setArticleId(String str) {
            articleId = str;
        }
    }
}
