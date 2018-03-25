package com.hongyan.xcj.modules.coin;

import android.os.Bundle;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;

public class CoinDetailActivity extends BaseActivity {

    private CoinView coinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        hideNavigationView();
        coinView = findViewById(R.id.coin_view);
    }
}
