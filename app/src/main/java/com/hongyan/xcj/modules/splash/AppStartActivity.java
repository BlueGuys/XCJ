package com.hongyan.xcj.modules.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.modules.main.MainTabActivity;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

public class AppStartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        hideNavigationView();
        handleAction();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AppStartActivity.this, MainTabActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void handleAction() {
        Uri uri = getIntent().getData();
        if (uri == null) {
            return;
        }
        String url = uri.getQueryParameter("url");
        String pageID = uri.getQueryParameter("pageID");
        BaseApplication.getInstance().setActionUrl(url);
        BaseApplication.getInstance().setPageID(pageID);
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
}
