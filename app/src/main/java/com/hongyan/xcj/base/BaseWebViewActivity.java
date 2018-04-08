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
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.modules.event.TokenMessageEvent;
import com.hongyan.xcj.utils.StringUtils;
import com.hongyan.xcj.widget.loading.WebViewProgressView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

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
    protected WebView mWebView;
    protected String mUrl;
    protected WebViewProgressView progressView;
    protected RelativeLayout imageBack;
    protected RelativeLayout rightLayout;
    protected ImageView imageCollection;
    protected TextView tvTitle;

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
        initView();
        mUrl = getIntent().getStringExtra(URL);
        setTitle();
        mWebView.loadUrl(mUrl);
//        mWebView.loadUrl("file:///android_asset/hello.html");
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initView() {
        mWebView = findViewById(R.id.webView);
        tvTitle = findViewById(R.id.webView_title);
        progressView = findViewById(R.id.progress);
        imageBack = findViewById(R.id.rl_left);
        imageCollection = findViewById(R.id.image_collection);
        rightLayout = findViewById(R.id.rl_right);
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

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String token = AccountManager.getInstance().getToken();
            mWebView.loadUrl("javascript:onTokenResult('" + token + "')");
            return true;
        }

        //        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            AlertDialog.Builder b = new AlertDialog.Builder(BaseWebViewActivity.this);
//            b.setTitle("Alert");
//            b.setMessage(message);
//            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    result.confirm();
//                }
//            });
//            b.setCancelable(false);
//            b.create().show();
//            return true;
//        }
    }

    private void setTitle() {
        if (tvTitle == null) {
            return;
        }
        if (mUrl == null || mUrl.length() == 0) {
            return;
        }
        Uri uri = Uri.parse(mUrl);
        String title = uri.getQueryParameter("title");
        if (StringUtils.isEmpty(title)) {
            tvTitle.setText("烯财经");
        } else {
            tvTitle.setText(title);
        }
    }

    public class ClientFunction {

        @JavascriptInterface
        public void finish() {
            BaseWebViewActivity.this.finish();
        }

        @JavascriptInterface
        public void setToken(String token) {
            AccountManager.getInstance().setToken(token);
        }

        @JavascriptInterface
        public void getToken() {
            String token = AccountManager.getInstance().getToken();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (StringUtils.isEmpty(token)) {
                        mWebView.loadUrl("javascript:onTokenResult('" + "" + "')");
                    } else {
                        mWebView.loadUrl("javascript:onTokenResult('" + token + "')");
                    }
                }
            });
        }

        @JavascriptInterface
        public void login() {
            if (!AccountManager.getInstance().isLogin()) {
                EventBus.getDefault().post(new TokenMessageEvent());
            }
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

}
