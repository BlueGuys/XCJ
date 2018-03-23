package com.hongyan.xcj.modules.main.market;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;

public class MarketToggleButton extends LinearLayout {

    private TextView tvLeft;
    private TextView tvRight;
    private boolean isLeft;
    private OnStatusChangeListener mListener;

    public MarketToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        View view = View.inflate(context, R.layout.btn_market_toggle, this);
        tvLeft = view.findViewById(R.id.tv_left);
        tvRight = view.findViewById(R.id.tv_right);
        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLeft) {
                    return;
                }
                slideLeft();
                if (mListener != null) {
                    mListener.onChange(0);
                }
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLeft) {
                    return;
                }
                slideRight();
                if (mListener != null) {
                    mListener.onChange(1);
                }
            }
        });
    }

    private void slideLeft() {
        tvLeft.setBackgroundResource(R.drawable.bg_button_market_white);
        tvLeft.setTextColor(getResources().getColor(R.color.market_blue_bg));
        tvRight.setTextColor(getResources().getColor(R.color.white));
        tvRight.setBackgroundColor(Color.TRANSPARENT);
        isLeft = true;
    }

    private void slideRight() {
        tvRight.setBackgroundResource(R.drawable.bg_button_market_white);
        tvRight.setTextColor(getResources().getColor(R.color.market_blue_bg));
        tvLeft.setTextColor(getResources().getColor(R.color.white));
        tvLeft.setBackgroundColor(Color.TRANSPARENT);
        isLeft = false;
    }

    public void setStatusChangeListener(OnStatusChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnStatusChangeListener {
        void onChange(int buttonId);
    }

}
