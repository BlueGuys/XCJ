package com.hongyan.xcj.modules.search;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by wangning on 2018/4/2.
 */

public class SearchAdapter extends BaseAdapter {

    private Activity activity;

    public SearchAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(){

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
