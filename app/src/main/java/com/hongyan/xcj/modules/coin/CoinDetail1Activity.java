package com.hongyan.xcj.modules.coin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.coin.widget.CoinView;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;

public class CoinDetail1Activity extends BaseActivity {

    private CoinView mCoinView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        mCoinView = findViewById(R.id.coin_view);
        requestCoinCore();
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
                    mCoinView.updateData(result.data);
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
}
