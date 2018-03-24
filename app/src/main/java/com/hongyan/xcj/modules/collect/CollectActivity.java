package com.hongyan.xcj.modules.collect;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.RecyclerItemClickListener;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.article.ArticleActivity;
import com.hongyan.xcj.modules.main.info.recommend.InfoRecommendAdapter;
import com.hongyan.xcj.modules.main.info.recommend.InfoRecommendResult;
import com.hongyan.xcj.modules.main.info.reports.InfoReportResult;
import com.hongyan.xcj.modules.main.market.MarketAdapter;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
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
    private SwipeToLoadHelper helper;
    private int currentPage = 1;
    private ArrayList<CollectionResult.Collection> mArticleList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        setTitle("我的收藏");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
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
        helper = new SwipeToLoadHelper(mRecyclerView, adapterWrapper);
        mRecyclerView.setAdapter(adapterWrapper);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                refresh();
            }
        });
        helper.setLoadMoreListener(new SwipeToLoadHelper.LoadMoreListener() {
            @Override
            public void onLoad() {
                loadMore();
            }
        });
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(CollectActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CollectionResult.Collection collection = mArticleList.get(position);
                        if (collection != null) {
                            ArticleActivity.startActivity(CollectActivity.this, collection.url);
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                })
        );
    }

    private void notifyDataSetChanged() {
        mAdapter.setData(mArticleList);
        adapterWrapper.notifyDataSetChanged();
    }

    private void refresh() {
        JPRequest request = new JPRequest<>(CollectionResult.class, UrlConst.getMyCollectUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                mRefreshLayout.setRefreshing(false);
                if (null == response || null == response.getResult()) {
                    return;
                }
                CollectionResult result = (CollectionResult) response.getResult();
                if (result != null && result.data != null) {
                    mArticleList.clear();
                    mArticleList.addAll(result.data.collectionList);
                    currentPage = 1;
                    notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        request.addParam("pagesize", "20");
        request.addParam("p", 1);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }

    private void loadMore() {
        JPRequest request = new JPRequest<>(CollectionResult.class, UrlConst.getMyCollectUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                helper.setLoadMoreFinish();
                if (null == response || null == response.getResult()) {
                    return;
                }
                CollectionResult result = (CollectionResult) response.getResult();
                if (result != null && result.data != null) {
                    mArticleList.addAll(result.data.collectionList);
                    boolean hasMore = "1".equals(result.data.hasMore);
                    notifyDataSetChanged();
                    if (hasMore) {
                        currentPage++;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        request.addParam("pagesize", "20");
        request.addParam("p", currentPage);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }

}
