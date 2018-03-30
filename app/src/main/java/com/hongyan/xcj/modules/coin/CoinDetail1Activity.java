package com.hongyan.xcj.modules.coin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.coin.widget.CoinDetailNavigation;
import com.hongyan.xcj.modules.coin.widget.CoinView;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

public class CoinDetail1Activity extends BaseActivity {

    private CoinView mCoinView;
    private CoinDetailNavigation mNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        initView();
        requestPageData();
        requestCoinCore();
    }

    private void initView() {
        hideNavigationView();
        mCoinView = findViewById(R.id.coin_view);
        mNavigation = findViewById(R.id.coin_navigation);
        mNavigation.setOnBackClickListener(new CoinDetailNavigation.OnBackClickListener() {
            @Override
            public void callBack() {
                finish();
            }
        });
        mNavigation.setOnRefreshClickListener(new CoinDetailNavigation.OnRefreshClickListener() {
            @Override
            public void refresh() {
                showSuccessToast("刷新");
            }
        });
        mNavigation.setOnSelectChangeListener(new CoinDetailNavigation.OnSelectChangeListener() {
            @Override
            public void onSelectChange(String coinId) {
                showErrorToast("刷新" + coinId);
            }
        });
        mNavigation.setOnCollectChangeListener(new CoinDetailNavigation.onCollectChangeListener() {
            @Override
            public void onCollectChange(String coinId, boolean isCollect) {
                if (isCollect) {
                    showErrorToast("收藏" + coinId);
                } else {
                    showErrorToast("取消收藏" + coinId);
                }
            }
        });
    }

    private void requestPageData() {
        JPRequest request = new JPRequest<>(CoinDetailResult.class, UrlConst.getCoinDetailUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                CoinDetailResult result = (CoinDetailResult) response.getResult();
                if (result != null && result.data != null) {
                    mNavigation.setCoinTitleList(result.data.titleList, 3);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }

    private void requestCoinCore() {
        JPRequest request = new JPRequest<>(CoinResult.class, UrlConst.getCoinCoreUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                CoinResult result = (CoinResult) response.getResult();
                if (result != null && result.data != null) {
                    mCoinView.updateData(result.data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }
}
