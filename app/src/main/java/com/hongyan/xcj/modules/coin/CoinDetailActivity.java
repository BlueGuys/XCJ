package com.hongyan.xcj.modules.coin;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.coin.widget.CoinDetailNavigation;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;


public class CoinDetailActivity extends BaseActivity {

    private CoinDetailNavigation mNavigation;
    private RecyclerView mRecyclerView;
    private CoinDetailAdapter mAdapter;

    private String coinID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        coinID = getIntent().getStringExtra("id");
        initView();
        requestPageData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestCoinCore();
            }
        }, 500);
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

    private void initView() {
        hideNavigationView();
        mNavigation = findViewById(R.id.coin_navigation);
        TextView tvCoinK = findViewById(R.id.tv_coin_K);
        TextView tvCoinDeal = findViewById(R.id.tv_coin_deal);
        TextView tvCoinDetail = findViewById(R.id.tv_coin_detail);
        mRecyclerView = findViewById(R.id.coin_recycler_view);
        mNavigation.setOnBackClickListener(new CoinDetailNavigation.OnBackClickListener() {
            @Override
            public void callBack() {
                finish();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CoinDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CoinDetailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
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
        tvCoinK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothMoveToPosition(mRecyclerView, 0);
            }
        });
        tvCoinDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothMoveToPosition(mRecyclerView, 1);
            }
        });
        tvCoinDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothMoveToPosition(mRecyclerView, 25);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mRecyclerView, mToPosition);
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
                    int currentID = 0;
                    if (result.data.titleList != null) {
                        for (int i = 0; i < result.data.titleList.size(); i++) {
                            CoinDetailResult.CoinTitleBean bean = result.data.titleList.get(i);
                            if (!StringUtils.isEmpty(bean.id) && bean.id.equals(coinID)) {
                                currentID = i;
                            }
                        }
                    }
                    mNavigation.setCoinTitleList(result.data.titleList, currentID);
                    mAdapter.setData(result.data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        request.addParam("id", coinID);
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

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}
