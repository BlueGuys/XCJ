package com.hongyan.xcj.modules.main.info.news;

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
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.test.AdapterWrapper;
import com.hongyan.xcj.test.DividerItemDecoration;
import com.hongyan.xcj.test.SwipeToLoadHelper;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private InfoNewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterWrapper adapterWrapper;
    private SwipeToLoadHelper helper;
    private int currentPage = 1;
    private ArrayList<InfoNewsResult.News> mNewsList = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news, container, false);
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
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new InfoNewsAdapter();
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
    }

    private void notifyDataSetChanged() {
        mAdapter.setData(mNewsList);
        adapterWrapper.notifyDataSetChanged();
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 8; i++) {
            data.add(i + temp);
        }
        return data;
    }

    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData() {
        for (int i = 0; i < 20; i++) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        JPRequest request = new JPRequest<>(InfoNewsResult.class, UrlConst.getInfoNewsUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                mRefreshLayout.setRefreshing(false);
                if (null == response || null == response.getResult()) {
                    return;
                }
                InfoNewsResult result = (InfoNewsResult) response.getResult();
                if (result != null && result.data != null) {
                    mNewsList.clear();
                    mNewsList.addAll(result.data.list);
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
        JPRequest request = new JPRequest<>(InfoNewsResult.class, UrlConst.getInfoNewsUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                helper.setLoadMoreFinish();
                if (null == response || null == response.getResult()) {
                    return;
                }
                InfoNewsResult result = (InfoNewsResult) response.getResult();
                if (result != null && result.data != null) {
                    mNewsList.addAll(result.data.list);
                    boolean hasMore = "1".equals(result.data.hasMore);
                    if (hasMore) {
                        currentPage++;
                    }else{
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
