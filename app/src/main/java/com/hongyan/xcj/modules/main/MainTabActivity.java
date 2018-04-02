package com.hongyan.xcj.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.BaseWebViewActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.core.AccountMessageEvent;
import com.hongyan.xcj.modules.main.info.InfoFragment;
import com.hongyan.xcj.modules.main.market.MarketFragment;
import com.hongyan.xcj.modules.main.me.MeFragment;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private long exitTime = 0;
    private final static long DOUBLE_BACK_TIME = 2000; // 两次back的间隔时间：2s

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavigationView();
        mViewPager = findViewById(R.id.vp_content);
        initContent();
        initTab();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initTab() {
        mTabLayout = findViewById(R.id.tl_tab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.setupWithViewPager(mViewPager);
        setTab(0, new int[]{R.drawable.icon_tab_index_n, R.drawable.icon_tab_index_s}, "资讯");
        setTab(1, new int[]{R.drawable.icon_tab_market_n, R.drawable.icon_tab_market_s}, "行情");
        setTab(2, new int[]{R.drawable.icon_tab_me_n, R.drawable.icon_tab_me_s}, "我的");
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabView tabView = (TabView) tab.getCustomView();
                if (tabView != null) {
                    tabView.setSelected(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabView tabView = (TabView) tab.getCustomView();
                if (tabView != null) {
                    tabView.setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTab(int index, int[] drawable, String text) {
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        if (tab == null) {
            return;
        }
        TabView tabView = new TabView(MainTabActivity.this);
        tabView.setResource(drawable, text);
        tab.setCustomView(tabView);
    }

    private void initContent() {
        tabFragments = new ArrayList<>();
        tabFragments.add(new InfoFragment());
        tabFragments.add(new MarketFragment());
        tabFragments.add(new MeFragment());
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(contentAdapter);
        mViewPager.setCurrentItem(0);
        //TODO 添加
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            return super.dispatchKeyEvent(event);
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > DOUBLE_BACK_TIME) {
                showErrorToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void accountEvent(TokenMessageEvent message) {
        if (message == null) {
            return;
        }
        BaseWebViewActivity.startActivity(this, "http://www.xicaijing.com/App/Users/login.html?title=登录");
    }

}
