package com.hongyan.xcj.modules.coin;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.coin.widget.CoinDetailNavigation;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.test.DividerItemDecoration;


public class CoinDetailActivity extends BaseActivity {

    private CoinDetailNavigation mNavigation;
    private RecyclerView mRecyclerView;
    private CoinDetailAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        initView();
        requestPageData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestCoinCore();
            }
        },500);
    }

    private void initView() {
        hideNavigationView();
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

        mRecyclerView = findViewById(R.id.coin_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CoinDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(CoinDetailActivity.this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CoinDetailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
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
                    mAdapter.setData(result.data);
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
                    mAdapter.updateKLine(result.data);
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
