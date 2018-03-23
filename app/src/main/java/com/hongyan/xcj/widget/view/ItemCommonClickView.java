package com.hongyan.xcj.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;

public class ItemCommonClickView extends LinearLayout {


    public ItemCommonClickView(Context context) {
        this(context, null);
    }

    public ItemCommonClickView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemCommonClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.item_common_click_view, this);
        TextView textView = view.findViewById(R.id.text_view);
        @SuppressLint("Recycle") TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ItemCommonClickView);
        String title = ta.getString(R.styleable.ItemCommonClickView_title);
        textView.setText(title);
    }
}
