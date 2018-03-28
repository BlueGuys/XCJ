package com.hongyan.xcj.modules.main.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hongyan.xcj.R;
import com.hongyan.xcj.utils.DeviceUtils;

public class InfoHeaderView extends LinearLayout {

    private EditText etSearch;
    private LinearLayout linearLayout;
    private int currentIndex = 0;
    private OnTabChangeListener mOnTabChangeListener;
    private OnSearchListener mOnSearchListener;

    @SuppressLint("ClickableViewAccessibility")
    public InfoHeaderView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_info_head_view, this);
        etSearch = view.findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mOnSearchListener != null) {
                        mOnSearchListener.onSearch(etSearch.getText().toString());
                    }
                }
                return false;
            }
        });
        Drawable drawable = getResources().getDrawable(R.drawable.icon_search);
        drawable.setBounds(0, 0, 50, 50);
        etSearch.setCompoundDrawables(drawable, null, null, null);
        initTabView(context, view);
    }

    private void initTabView(Context context, View view) {
        linearLayout = view.findViewById(R.id.bottomLinear);
        addView(context, "推荐");
//        addView(context, "快讯");
        addView(context, "分析师说");
        addView(context, "研报");
        linearLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int touchIndex = 0;
                int subViewWidth = linearLayout.getWidth() / 3;
                float x = motionEvent.getX();
                if (x > 0 && x < subViewWidth) {
                    touchIndex = 0;
                } else if (x > subViewWidth && x < subViewWidth * 2) {
                    touchIndex = 1;
                } else if (x > subViewWidth * 2 && x < subViewWidth * 3) {
                    touchIndex = 2;
                }
//                else {
//                    touchIndex = 3;
//                }
                if (touchIndex == currentIndex) {
                    return true;
                }
                for (int i = 0; i < 3; i++) {
                    TextView tv = (TextView) linearLayout.getChildAt(i);
                    TextPaint tp = tv.getPaint();
                    tp.setFakeBoldText(i == touchIndex);
                    tv.setText(tv.getText());
                    tv.setTextSize(i == touchIndex ? 17 : 14);
                    currentIndex = touchIndex;
                }
                if (mOnTabChangeListener != null) {
                    mOnTabChangeListener.onChange(touchIndex);
                }
                return true;
            }
        });
    }


    private void addView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        if ("推荐".equals(text)) {
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
        }
        textView.setText(text);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        linearLayout.addView(textView);
    }

    public void setCurrentIndex(int index) {
        for (int i = 0; i < 3; i++) {
            TextView tv = (TextView) linearLayout.getChildAt(i);
            TextPaint tp = tv.getPaint();
            tp.setFakeBoldText(i == index);
            tv.setText(tv.getText());
            tv.setTextSize(i == index ? 17 : 14);
        }
        currentIndex = index;
    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        this.mOnTabChangeListener = listener;
    }

    public void setOnSearchListener(OnSearchListener listener) {
        this.mOnSearchListener = listener;
    }

    public interface OnTabChangeListener {
        void onChange(int index);
    }

    public interface OnSearchListener {
        void onSearch(String text);
    }
}
