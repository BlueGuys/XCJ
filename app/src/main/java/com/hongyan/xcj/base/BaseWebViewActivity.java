package com.hongyan.xcj.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hongyan.xcj.core.AccountManager;

import com.hongyan.xcj.R;
import com.hongyan.xcj.widget.loading.WebViewProgressView;

/**
 * com.jp.base.BaseWebViewActivity
 *
 * @author wangning
 * @version 2.1.0
 * @date 2017/7/6:08:27
 * @desc
 */
public class BaseWebViewActivity extends BaseActivity {


    public static final String URL = "url";
    private WebView mWebView;
    private String mUrl;
    private WebViewProgressView progressView;
    private RelativeLayout imageBack;
    private RelativeLayout rightLayout;
    private ImageView imageCollection;
    private TextView tvTitle;
    private boolean isCollection = false;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, BaseWebViewActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        hideNavigationView();
        initData();
        initView();
        load(mUrl);
    }

    protected void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(URL);
    }

    private void load(String url) {
        if (mWebView == null) {
            return;
        }
        if (url == null || url.length() == 0) {
            return;
        }
        Uri uri = Uri.parse(url);
        String type = uri.getQueryParameter("title");
        setTitle(type);
//        mWebView.loadUrl(url);
        mWebView.loadUrl("file:///android_asset/hello.html");
    }

    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initView() {
        mWebView = findViewById(R.id.webView);
        tvTitle = findViewById(R.id.webView_title);
        progressView = findViewById(R.id.progress);
        imageBack = findViewById(R.id.rl_left);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();//返回上一页面
                } else {
                    finish();
                }
            }
        });
        imageCollection = findViewById(R.id.image_collection);
        rightLayout = findViewById(R.id.rl_right);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCollection) {
                    mWebView.loadUrl("javascript:cancelCollection()");
                    isCollection = false;
                } else {
                    mWebView.loadUrl("javascript:collection()");
                    isCollection = true;
                }
                imageCollection.setImageResource(isCollection ? R.drawable.icon_collection : R.drawable.icon_cancel_collection);
            }
        });


        try {
            mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setGeolocationEnabled(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setSupportMultipleWindows(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setTextZoom(100);

            if (Build.VERSION.SDK_INT >= 19) {
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            }
            mWebView.requestFocus(View.FOCUS_DOWN);

            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.setWebViewClient(mWebViewClient);
            mWebView.setWebChromeClient(new MyWebChromeClient());
            mWebView.addJavascriptInterface(new ClientFunction(), "webView");
//            mWebView.evaluateJavascript("javascript:collection()", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//                    //此处为 js 返回的结果
//                }
//            });
        } catch (Exception e) {
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                progressView.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (mWebView != null) {
                BaseWebViewActivity.startActivity(BaseWebViewActivity.this, url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class ClientFunction {
        @JavascriptInterface
        public void collection(String str) {
            isCollection = "0".equals(str);
            imageCollection.setImageResource(isCollection ? R.drawable.icon_collection : R.drawable.icon_cancel_collection);
        }

        @JavascriptInterface
        public void finish() {
            BaseWebViewActivity.this.finish();
        }

        @JavascriptInterface
        public void setToken(String token) {
            AccountManager.getInstance().setToken(token);
        }
    }

    Runnable run2 = new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                View rootview = BaseWebViewActivity.this.getWindow().getDecorView();
                View aaa = rootview.findFocus();
                Log.e("test", aaa.toString());
            }

        }
    };

}
