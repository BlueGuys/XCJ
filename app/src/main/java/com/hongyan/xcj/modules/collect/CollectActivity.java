package com.hongyan.xcj.modules.collect;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.modules.main.market.MarketAdapter;
import com.hongyan.xcj.test.AdapterWrapper;
import com.hongyan.xcj.test.DividerItemDecoration;
import com.hongyan.xcj.test.SwipeToLoadHelper;

import java.util.ArrayList;


public class CollectActivity extends BaseActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CollectionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterWrapper adapterWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        setTitle("我的收藏");
        initView();
        notifyDataSetChanged();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(CollectActivity.this, LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(CollectActivity.this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CollectionAdapter();
        adapterWrapper = new AdapterWrapper(mAdapter);
        final SwipeToLoadHelper helper = new SwipeToLoadHelper(mRecyclerView, adapterWrapper);
        mRecyclerView.setAdapter(adapterWrapper);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //我在List最前面加入一条数据
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        helper.setLoadMoreListener(new SwipeToLoadHelper.LoadMoreListener() {
            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        helper.setLoadMoreFinish();
                    }
                }, 1000);
            }
        });
    }

    private void notifyDataSetChanged() {
        mAdapter.setData(getData());
        adapterWrapper.notifyDataSetChanged();
    }

    private ArrayList<CollectionResult.Collection> getData() {
        ArrayList<CollectionResult.Collection> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 8; i++) {
            data.add(new CollectionResult.Collection());
        }
        return data;
    }
}
