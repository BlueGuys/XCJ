package com.hongyan.xcj.modules.main.info;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter
 * ViewPager加载fragment时使用
 * 主要实现两个方法，加一个构造方法，可以在构造方法中传一个构造好的fragment集合和context
 * Created by Devin Chen on 2016/12/17.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles = {"天九", "地八", "人七", "和五"};//构造传递给fragment用于不同显示内容的参数
    List<Fragment> mList = new ArrayList<>();

    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    /**
     * 根据id生成fragment，写好这个就好了
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    /**
     * 可以使已知数，也可以是一个集合的长度
     *
     * @return
     */
    @Override
    public int getCount() {
        return titles.length;
    }
}