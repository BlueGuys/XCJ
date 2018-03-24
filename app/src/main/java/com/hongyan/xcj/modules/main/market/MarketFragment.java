package com.hongyan.xcj.modules.main.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;
import com.hongyan.xcj.modules.main.market.all.MarketAllFragment;
import com.hongyan.xcj.modules.main.market.my.MarketMyFragment;

public class MarketFragment extends BaseFragment {

    private MarketToggleButton toggleButton;
    private MarketAllFragment marketAllFragment = new MarketAllFragment();
    private MarketMyFragment marketMyFragment = new MarketMyFragment();
    private FragmentManager fragmentManager;

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
                    getTransaction().replace(R.id.fragment_layout, marketAllFragment).commit();
                } else {
                    getTransaction().replace(R.id.fragment_layout, marketMyFragment).commit();
                }
            }
        });
        getTransaction().add(R.id.fragment_layout, marketAllFragment).commit();
    }

    private FragmentTransaction getTransaction() {
        if (fragmentManager == null) {
            fragmentManager = getFragmentManager();
        }
        return fragmentManager.beginTransaction();
    }
}
