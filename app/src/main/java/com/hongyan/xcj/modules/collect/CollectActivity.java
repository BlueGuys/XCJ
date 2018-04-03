package com.hongyan.xcj.modules.collect;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.RecyclerItemClickListener;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.core.AccountManager;
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
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;


public class CollectActivity extends BaseActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private CollectionAdapter mAdapter;
    private AdapterWrapper adapterWrapper;
    private SwipeToLoadHelper helper;
    private int currentPage = 1;
    private ArrayList<CollectionResult.Collection> mArticleList = new ArrayList<>();
    private TextView tvNotLogin;

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
        MobclickAgent.onResume(this);
        if (AccountManager.getInstance().isLogin()) {
            refresh();
            tvNotLogin.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            tvNotLogin.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        tvNotLogin = findViewById(R.id.tv_login_text);
        initNoDataView();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CollectActivity.this, LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
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

    private void initNoDataView() {
        String text = "现在登录，选择要特别关注的资讯主题告别被快讯刷屏的困扰";
        SpannableString spStr = new SpannableString(text);
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.xcj_blue));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                AccountManager.getInstance().login();
            }
        }, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvNotLogin.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tvNotLogin.append(spStr);
        tvNotLogin.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
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
                    helper.setSwipeToLoadEnabled("1".equals(result.data.hasMore));
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
                    if (hasMore) {
                        currentPage++;
                    } else {
                        helper.setSwipeToLoadEnabled(false);
                    }
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
        request.addParam("p", currentPage);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }

}
