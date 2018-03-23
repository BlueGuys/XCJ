package com.hongyan.xcj.modules.main.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;

public class InfoFragment extends BaseFragment {

    private InfoHeaderView headerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        headerView = view.findViewById(R.id.headView);
        headerView.setOnTabChangeListener(new InfoHeaderView.OnTabChangeListener() {
            @Override
            public void onChange(int index) {
                showSuccessToast("切换为" + index);
            }
        });
        headerView.setmOnSearchListener(new InfoHeaderView.OnSearchListener() {
            @Override
            public void onSearch(String text) {
                showSuccessToast("搜索文案" + text);
            }
        });
        return view;
    }
}
