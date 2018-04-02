package com.hongyan.xcj.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hongyan.xcj.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class RecycleViewActivity extends Activity {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        initView();
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
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(getData());

        AdapterWrapper adapterWrapper = new AdapterWrapper(mAdapter);
        final SwipeToLoadHelper helper = new SwipeToLoadHelper(mRecyclerView, adapterWrapper);
        mRecyclerView.setAdapter(adapterWrapper);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //我在List最前面加入一条数据
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                },1000);
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
                },1000);
            }
        });

    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 20; i++) {
            data.add(i + temp);
        }
        return data;
    }

    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData(){
        for (int i =0; i < 10; i++){
            mAdapter.notifyDataSetChanged();
        }
    }

}
