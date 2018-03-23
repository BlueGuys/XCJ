package com.hongyan.xcj.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;

/**
 * com.jp.views.NavigationView
 *
 * @author wangning
 * @version 2.1.0
 * @date 2017/7/5:08:28
 * @desc
 */
public class NavigationView extends LinearLayout {

    private Context mContext;
    private View view;
    private RelativeLayout mLeftLayout;
    private RelativeLayout mCenterLayout;
    private RelativeLayout mRightLayout;
    private TextView textView;

    public NavigationView(Context context) {
        super(context);
        initView(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {
        this.mContext = context;
        setBackgroundColor(Color.WHITE);
        view = View.inflate(context, R.layout.view_navigation, this);
        mLeftLayout = (RelativeLayout) view.findViewById(R.id.layout_left);
        mCenterLayout = (RelativeLayout) view.findViewById(R.id.layout_center);
        mRightLayout = (RelativeLayout) view.findViewById(R.id.layout_right);
        textView = (TextView) view.findViewById(R.id.title);
    }

    public void setTitle(String title) {
        textView.setText(title);
    }

    public void setOnBackClickListener(final OnBackClickListener onBackClickListener) {
        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onBackClickListener != null) {
                    onBackClickListener.callBack();
                }
            }
        });
    }

    public interface OnBackClickListener {

        void callBack();
    }

}
