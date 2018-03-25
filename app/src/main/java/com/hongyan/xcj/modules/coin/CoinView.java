package com.hongyan.xcj.modules.coin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hongyan.xcj.R;


public class CoinView extends LinearLayout {

    private View view;

    public CoinView(Context context) {
        super(context);
    }

    public CoinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = View.inflate(context, R.layout.view_coin, this);
    }

}
