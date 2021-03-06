package com.hongyan.xcj.modules.main.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseFragment;
import com.hongyan.xcj.base.BaseWebViewActivity;
import com.hongyan.xcj.core.AccountInfo;
import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.core.AccountMessageEvent;
import com.hongyan.xcj.modules.coin.CoinDetailActivity;
import com.hongyan.xcj.modules.collect.CollectActivity;
import com.hongyan.xcj.widget.view.ItemCommonClickView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvAccount;
    private TextView tvLogin;
    private ImageView ivLogo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        tvLogin = view.findViewById(R.id.tv_login);
        tvAccount = view.findViewById(R.id.tv_account);
        ivLogo = view.findViewById(R.id.logo);
        ItemCommonClickView linearCollection = view.findViewById(R.id.linear_collection);
        ItemCommonClickView linearMarket = view.findViewById(R.id.linear_market);
        ItemCommonClickView linearClear = view.findViewById(R.id.linear_clear);
        ItemCommonClickView linearLogout = view.findViewById(R.id.linear_logout);
        tvLogin.setOnClickListener(this);
        linearCollection.setOnClickListener(this);
        linearMarket.setOnClickListener(this);
        linearClear.setOnClickListener(this);
        linearLogout.setOnClickListener(this);
        checkLogin(AccountManager.getInstance().isLogin());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                BaseWebViewActivity.startActivity(getActivity(), "http://www.xicaijing.com/App/Users/login.html?title=登录");
                break;
            case R.id.linear_collection:
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.linear_market:
                break;
            case R.id.linear_clear:
                break;
            case R.id.linear_logout:
                AccountManager.getInstance().logout();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void accountEvent(AccountMessageEvent message) {
        if (message == null) {
            return;
        }
        checkLogin(message.isLogin());
    }

    private void checkLogin(boolean isLogin) {
        if (isLogin) {
            ivLogo.setImageResource(R.drawable.icon_account_login);
            tvLogin.setVisibility(View.GONE);
            tvAccount.setVisibility(View.VISIBLE);
            AccountInfo info = AccountManager.getInstance().getAccountInfo();
            if (info != null) {
                tvAccount.setText(info.getEmail());
            }
        } else {
            ivLogo.setImageResource(R.drawable.icon_account_logout);
            tvLogin.setVisibility(View.VISIBLE);
            tvAccount.setVisibility(View.GONE);
        }
    }
}
