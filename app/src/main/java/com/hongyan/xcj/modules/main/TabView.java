package com.hongyan.xcj.modules.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;

public class TabView extends LinearLayout {

    private View view;
    private ImageView iv;

    private int[] icons;

    private boolean isSelected;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout_custom, this, true);
        iv = view.findViewById(R.id.logo);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        updateView();
    }

    /**
     * 设置资源
     *
     * @param drawable
     * @param text
     */
    public void setResource(int[] drawable, String text) {
        this.icons = drawable;
        updateView();
    }

    private void updateView() {
        iv.setImageResource(isSelected ? icons[1] : icons[0]);
    }
}
