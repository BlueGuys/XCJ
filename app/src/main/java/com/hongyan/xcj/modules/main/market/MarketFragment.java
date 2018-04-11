package com.hongyan.xcj.modules.main.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;
import com.hongyan.xcj.modules.event.MarketMessageEvent;
import com.hongyan.xcj.modules.main.market.all.MarketAllFragment;
import com.hongyan.xcj.modules.main.market.my.MarketMyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MarketFragment extends BaseFragment {

    private MarketAllFragment marketAllFragment = new MarketAllFragment();
    private MarketMyFragment marketMyFragment = new MarketMyFragment();
    private FragmentManager fragmentManager;
    private MarketToggleButton toggleButton;
    private boolean isAttch = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toggleButton = view.findViewById(R.id.btn_toggle);
        toggleButton.setStatusChangeListener(new MarketToggleButton.OnStatusChangeListener() {
            @Override
            public void onChange(int buttonId) {
                if (buttonId == 0) {
                    switchFragment(0);
                } else {
                    switchFragment(1);
                }
            }
        });
        switchFragment(0);
    }

    private FragmentTransaction getTransaction() {
        if (fragmentManager == null) {
            fragmentManager = getFragmentManager();
        }
        return fragmentManager.beginTransaction();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void marketFragment(MarketMessageEvent message) {
        if (message == null) {
            return;
        }
        switchFragment(message.frgmentID);
        if(message.frgmentID == 0){
            toggleButton.slideLeft();
        }else{
            toggleButton.slideRight();
        }
    }

    private void switchFragment(int type) {
        if (isAttch) {
            if (type == 0) {
                getTransaction().show(marketAllFragment).hide(marketMyFragment).commit();
            } else {
                getTransaction().show(marketMyFragment).hide(marketAllFragment).commit();
            }
        } else {
            getTransaction().add(R.id.fragment_layout, marketAllFragment).add(R.id.fragment_layout, marketMyFragment).show(marketAllFragment).hide(marketMyFragment).commit();
            isAttch = true;
        }
    }
}
