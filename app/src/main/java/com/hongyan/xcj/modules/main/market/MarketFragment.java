package com.hongyan.xcj.modules.main.market;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class MarketFragment extends BaseFragment {

    private View view;
    private MarketToggleButton toggleButton;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MarketAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterWrapper adapterWrapper;
    private SwipeToLoadHelper helper;
    private ArrayList<MarketResult.Market> mAllMarketList = new ArrayList<>();
    private ArrayList<MarketResult.Market> mMeMarketList = new ArrayList<>();

    /**
     * 是全部列表
     */
    private boolean isAll = true;

    //分页标志
    private int page=1;

    private boolean hasmore=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_market, container, false);
        toggleButton = view.findViewById(R.id.btn_toggle);
        toggleButton.setStatusChangeListener(new MarketToggleButton.OnStatusChangeListener() {
            @Override
            public void onChange(int buttonId) {
                isAll = buttonId == 0;
            }
        });
        initView();
        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MarketAdapter();
        adapterWrapper = new AdapterWrapper(mAdapter);
        helper = new SwipeToLoadHelper(mRecyclerView, adapterWrapper);
        mRecyclerView.setAdapter(adapterWrapper);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //我在List最前面加入一条数据
//                        mAdapter.notifyDataSetChanged();
//                        mRefreshLayout.setRefreshing(false);
//                    }
//                }, 1000);

                registerMore(true);

            }
        });
        helper.setLoadMoreListener(new SwipeToLoadHelper.LoadMoreListener() {
            @Override
            public void onLoad() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        helper.setLoadMoreFinish();
//                    }
//                }, 1000);
                registerMore(false);
            }
        });
    }

    private void notifyDataSetChanged() {
        mAdapter.setData(isAll ? mAllMarketList : mMeMarketList);
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

        registerMore(false);
    }

    /**
     * 向鉴权中心注册
     */
    private void registerMore(final boolean refresh) {
        if(refresh){
            if (isAll) {
                mAllMarketList.clear();
            } else {
                mMeMarketList.clear();
            }
            page=1;
        }
        JPRequest request = new JPRequest<>(MarketResult.class, UrlConst.getMarketList(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if(refresh){
                    mRefreshLayout.setRefreshing(false);
                }else{ helper.setLoadMoreFinish();
                }
                if (null == response || null == response.getResult()) {;
                    return;
                }
                MarketResult result = (MarketResult) response.getResult();
                if (result != null && result.data != null) {
                    hasmore= "1".equals(result.data.hasMore);
                    setData(result.data.marketList);
                    notifyDataSetChanged();
                    page=+1;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getErrorMessage());
            }
        });
        request.addParam("pagesize", "10");
        request.addParam("p", page);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }

    private void setData(ArrayList<MarketResult.Market> list) {
        if (isAll) {
            mAllMarketList.addAll(list);
        } else {
            mMeMarketList.addAll(list);
        }
        notifyDataSetChanged();
    }


//    /**
//     * 向鉴权中心注册
//     */
//    private void register() {
//        JPRequest request = new JPRequest<>(MarketResult.class, UrlConst.getMarketList(), new Response.Listener<JPResponse>() {
//            @Override
//            public void onResponse(JPResponse response) {
//                if (null == response || null == response.getResult()) {
//                    return;
//                }
//                MarketResult result = (MarketResult) response.getResult();
//                if (result != null && result.data != null) {
//                    setData(result.data.marketList);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error", error.getErrorMessage());
//            }
//        });
//        request.addParam("pagesize", "10");
//        request.addParam("p", "1");
//        JPBaseModel baseModel = new JPBaseModel();
//        baseModel.sendRequest(request);
//    }


}
