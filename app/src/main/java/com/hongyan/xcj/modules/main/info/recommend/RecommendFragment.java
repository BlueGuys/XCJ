package com.hongyan.xcj.modules.main.info.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.RecyclerItemClickListener;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.article.ArticleActivity;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.test.AdapterWrapper;
import com.hongyan.xcj.test.DividerItemDecoration;
import com.hongyan.xcj.test.SwipeToLoadHelper;

import java.util.ArrayList;

public class RecommendFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private InfoRecommendAdapter mAdapter;
    private AdapterWrapper adapterWrapper;
    private SwipeToLoadHelper helper;
    private int currentPage = 1;
    private ArrayList<InfoRecommendResult.Article> mArticleList = new ArrayList<>();
    private ArrayList<InfoRecommendResult.AD> mAdList = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recommend, container, false);
            initView();
            refresh();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void initView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new InfoRecommendAdapter(getActivity());
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
                new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0) {//点击HeaderView事件单独处理
                            return;
                        }
                        InfoRecommendResult.Article article = (InfoRecommendResult.Article) mAdapter.getItemData(position);
                        if (article != null) {
                            ArticleActivity.startActivity(getActivity(), article.url);
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                })
        );
    }

    private void notifyDataSetChanged() {
        mAdapter.setData(mArticleList, mAdList);
        adapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refresh() {
        JPRequest request = new JPRequest<>(InfoRecommendResult.class, UrlConst.getInfoRecommendUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                mRefreshLayout.setRefreshing(false);
                if (null == response || null == response.getResult()) {
                    return;
                }
                InfoRecommendResult result = (InfoRecommendResult) response.getResult();
                if (result != null && result.data != null) {
                    if (result.data.list != null) {
                        mArticleList.clear();
                        mArticleList.addAll(result.data.list);
                    }
                    if (result.data.ad != null) {
                        mAdList.clear();
                        mAdList.addAll(result.data.ad);
                    }
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
        JPRequest request = new JPRequest<>(InfoRecommendResult.class, UrlConst.getInfoRecommendUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                helper.setLoadMoreFinish();
                if (null == response || null == response.getResult()) {
                    return;
                }
                InfoRecommendResult result = (InfoRecommendResult) response.getResult();
                if (result != null && result.data != null) {
                    mArticleList.addAll(result.data.list);
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
