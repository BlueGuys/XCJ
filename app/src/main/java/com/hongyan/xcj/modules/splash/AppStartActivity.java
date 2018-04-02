package com.hongyan.xcj.modules.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.modules.main.MainTabActivity;
import com.umeng.analytics.MobclickAgent;

public class AppStartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        hideNavigationView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AppStartActivity.this, MainTabActivity.class);
                startActivity(intent);
                finish();
            }
        }, 200);
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
