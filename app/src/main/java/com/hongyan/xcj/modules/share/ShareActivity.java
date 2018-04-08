package com.hongyan.xcj.modules.share;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

public class ShareActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        hideNavigationView();

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