package com.hongyan.xcj.modules.main.market.my;

import android.graphics.Color;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.modules.event.MarketMessageEvent;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.test.AdapterWrapper;
import com.hongyan.xcj.test.DividerItemDecoration;
import com.hongyan.xcj.test.SwipeToLoadHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MarketMyFragment extends BaseFragment {

    private RelativeLayout layoutNotLogin;
    private ImageView imageAddCoin;
    private TextView tvLogin;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MarketAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterWrapper adapterWrapper;
    private SwipeToLoadHelper helper;
    private ArrayList<MarketResult.Market> mMyMarketList = new ArrayList<>();
    private int currentPage = 1;

    private boolean isIntentAddCoin = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        refresh();
    }

    private void initView(View view) {
        layoutNotLogin = view.findViewById(R.id.linear_not_login);
        imageAddCoin = view.findViewById(R.id.image_add_coin);
        tvLogin = view.findViewById(R.id.tv_login_text);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MarketAdapter(getActivity());
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
        imageAddCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIntentAddCoin = true;
                AccountManager.getInstance().login();
            }
        });
        initNoDataView();
    }

    private void initNoDataView() {
        String text = "登录添加自选，开启币神之路";
        SpannableString spStr = new SpannableString(text);
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.xcj_blue));       //设置文件颜色
            }

            @Override
            public void onClick(View widget) {
                AccountManager.getInstance().login();
            }
        }, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogin.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tvLogin.append(spStr);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    private void notifyDataSetChanged() {
        mAdapter.setData(mMyMarketList);
        adapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.getInstance().isLogin()) {
            mRefreshLayout.setVisibility(View.VISIBLE);
            layoutNotLogin.setVisibility(View.GONE);
        } else {
            mRefreshLayout.setVisibility(View.GONE);
            layoutNotLogin.setVisibility(View.VISIBLE);
        }
        if (isIntentAddCoin) {
            if (AccountManager.getInstance().isLogin()) {
                EventBus.getDefault().post(new MarketMessageEvent(0));
            }
            isIntentAddCoin = false;
        }
    }

    private void refresh() {
        currentPage = 1;
        JPRequest request = new JPRequest<>(MarketResult.class, UrlConst.getMarketMyList(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                mRefreshLayout.setRefreshing(false);
                if (null == response || null == response.getResult()) {
                    return;
                }
                MarketResult result = (MarketResult) response.getResult();
                if (result != null && result.data != null) {
                    mMyMarketList.clear();
                    mMyMarketList.addAll(result.data.marketList);
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
        request.addParam("p", currentPage);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }

    private void loadMore() {
        currentPage ++;
        JPRequest request = new JPRequest<>(MarketResult.class, UrlConst.getMarketMyList(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                helper.setLoadMoreFinish();
                if (null == response || null == response.getResult()) {
                    return;
                }
                MarketResult result = (MarketResult) response.getResult();
                if (result != null && result.data != null) {
                    mMyMarketList.addAll(result.data.marketList);
                    boolean hasMore = "1".equals(result.data.hasMore);
                    if (!hasMore) {
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
