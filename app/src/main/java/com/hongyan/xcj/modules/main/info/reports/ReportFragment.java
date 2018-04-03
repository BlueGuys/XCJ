package com.hongyan.xcj.modules.main.info.reports;

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
import com.hongyan.xcj.modules.event.CollectMessageEvent;
import com.hongyan.xcj.modules.main.info.analysis.InfoAnalysisResult;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.test.AdapterWrapper;
import com.hongyan.xcj.test.DividerItemDecoration;
import com.hongyan.xcj.test.SwipeToLoadHelper;
import com.hongyan.xcj.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ReportFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private InfoReportAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterWrapper adapterWrapper;
    private SwipeToLoadHelper helper;
    private int currentPage = 1;
    private ArrayList<InfoReportResult.Report> mReportList = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_report, container, false);
            initView();
            refresh();
        }
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
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
        mAdapter = new InfoReportAdapter(getActivity());
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
        mAdapter.setData(mReportList);
        adapterWrapper.notifyDataSetChanged();
    }


    private void refresh() {
        JPRequest request = new JPRequest<>(InfoReportResult.class, UrlConst.getInfoReportUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                mRefreshLayout.setRefreshing(false);
                if (null == response || null == response.getResult()) {
                    return;
                }
                InfoReportResult result = (InfoReportResult) response.getResult();
                if (result != null && result.data != null) {
                    mReportList.clear();
                    mReportList.addAll(result.data.list);
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
        JPRequest request = new JPRequest<>(InfoReportResult.class, UrlConst.getInfoReportUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                helper.setLoadMoreFinish();
                if (null == response || null == response.getResult()) {
                    return;
                }
                InfoReportResult result = (InfoReportResult) response.getResult();
                if (result != null && result.data != null) {
                    mReportList.addAll(result.data.list);
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

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void collectMessage(CollectMessageEvent message) {
        if (message == null) {
            return;
        }
        String id = message.getId();
        String action = message.getAction();
        String type = message.getType();
        if (StringUtils.isEmpty(id)) {
            return;
        }
        if (StringUtils.isEmpty(type) || "0".equals(type)) {
            return;
        }
        if (StringUtils.isEmpty(action)) {
            return;
        }
        for (int i = 0; i < mReportList.size(); i++) {
            InfoReportResult.Report report = mReportList.get(i);
            if (id.equals(report.id)) {
                if ("1".equals(action)) {
                    mReportList.get(i).setCollect(true);
                    notifyDataSetChanged();
                } else if ("2".equals(action)) {
                    mReportList.get(i).setCollect(false);
                    notifyDataSetChanged();
                }
                break;
            }
        }
    }

}
