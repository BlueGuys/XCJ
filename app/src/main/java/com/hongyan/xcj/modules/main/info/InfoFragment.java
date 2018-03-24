package com.hongyan.xcj.modules.main.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;
import com.hongyan.xcj.modules.main.info.analysis.AnalysisFragment;
import com.hongyan.xcj.modules.main.info.news.NewsFragment;
import com.hongyan.xcj.modules.main.info.recommend.RecommendFragment;
import com.hongyan.xcj.modules.main.info.reports.ReportFragment;

import java.util.ArrayList;

public class InfoFragment extends BaseFragment {

    private InfoHeaderView headerView;
    private ViewPager mViewPager;
    private MyFragmentAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        mViewPager = view.findViewById(R.id.view_pager);
        fragments.add(new RecommendFragment());
        fragments.add(new NewsFragment());
        fragments.add(new AnalysisFragment());
        fragments.add(new ReportFragment());
        adapter = new MyFragmentAdapter(getFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(adapter);
    }
}
