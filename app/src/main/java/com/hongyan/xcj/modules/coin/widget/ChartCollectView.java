package com.hongyan.xcj.modules.coin.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.utils.DisplayUtils;

public class ChartCollectView extends LinearLayout implements View.OnClickListener {


    private TextView[] textViews;
    private OnSelectChangeListener mListener;
    private int currentIndex = 0;

    @SuppressLint("ClickableViewAccessibility")
    public ChartCollectView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.item_chart_selection, this);
        TextView tv00 = findViewById(R.id.tv_00);
        TextView tv01 = findViewById(R.id.tv_01);
        TextView tv02 = findViewById(R.id.tv_02);
        TextView tv03 = findViewById(R.id.tv_03);
        TextView tv04 = findViewById(R.id.tv_04);
        TextView tv05 = findViewById(R.id.tv_05);
        TextView tv06 = findViewById(R.id.tv_06);
        TextView tv07 = findViewById(R.id.tv_07);
        TextView tv08 = findViewById(R.id.tv_08);
        TextView tv09 = findViewById(R.id.tv_09);
        tv00.setOnClickListener(this);
        tv01.setOnClickListener(this);
        tv02.setOnClickListener(this);
        tv03.setOnClickListener(this);
        tv04.setOnClickListener(this);
        tv05.setOnClickListener(this);
        tv06.setOnClickListener(this);
        tv07.setOnClickListener(this);
        tv08.setOnClickListener(this);
        tv09.setOnClickListener(this);
        textViews = new TextView[10];
        textViews[0] = tv00;
        textViews[1] = tv01;
        textViews[2] = tv02;
        textViews[3] = tv03;
        textViews[4] = tv04;
        textViews[5] = tv05;
        textViews[6] = tv06;
        textViews[7] = tv07;
        textViews[8] = tv08;
        textViews[9] = tv09;
    }

    public void setOnSelectChangeListener(OnSelectChangeListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_00:
                currentIndex = 0;
                break;
            case R.id.tv_01:
                currentIndex = 1;
                break;
            case R.id.tv_02:
                currentIndex = 2;
                break;
            case R.id.tv_03:
                currentIndex = 3;
                break;
            case R.id.tv_04:
                currentIndex = 4;
                break;
            case R.id.tv_05:
                currentIndex = 5;
                break;
            case R.id.tv_06:
                currentIndex = 6;
                break;
            case R.id.tv_07:
                currentIndex = 7;
                break;
            case R.id.tv_08:
                currentIndex = 8;
                break;
            case R.id.tv_09:
                currentIndex = 9;
                break;
        }
        if (mListener != null) {
            mListener.change(currentIndex);
        }
        for (int i = 0; i < textViews.length; i++) {
            if (currentIndex == i) {
                textViews[i].setTextColor(getResources().getColor(R.color.white));
            } else {
                textViews[i].setTextColor(getResources().getColor(R.color.xcj_text_b));
            }
        }
    }

    public interface OnSelectChangeListener {
        void change(int index);
    }

}
