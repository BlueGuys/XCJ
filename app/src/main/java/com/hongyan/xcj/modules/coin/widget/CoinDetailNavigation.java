package com.hongyan.xcj.modules.coin.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.coin.CoinDetailResult;
import com.hongyan.xcj.modules.coin.CoinResult;
import com.hongyan.xcj.utils.DateUtils;
import com.hongyan.xcj.utils.JavaTypesHelper;
import com.hongyan.xcj.widget.NavigationView;

import java.util.ArrayList;
import java.util.Date;

public class CoinDetailNavigation extends LinearLayout {

    private ImageView imageAdd;
    private TextView tvTitle;

    private OnBackClickListener mOnBackClickListener;
    private OnRefreshClickListener mOnRefreshClickListener;
    private OnSelectChangeListener mOnSelectChangeListener;
    private onCollectChangeListener mOnCollectChangeListener;
    private ArrayList<CoinDetailResult.CoinTitleBean> mTitleList = new ArrayList<>();

    private int currentIndex;

    public CoinDetailNavigation(Context context) {
        super(context);
    }

    public CoinDetailNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_coin_navigation, this);
        ImageView imageBack = view.findViewById(R.id.icon_navigation_back);
        imageAdd = view.findViewById(R.id.icon_navigation_add);
        ImageView imageLeft = view.findViewById(R.id.icon_navigation_left);
        ImageView imageRight = view.findViewById(R.id.icon_navigation_right);
        ImageView imageRefresh = view.findViewById(R.id.icon_navigation_refresh);
        tvTitle = view.findViewById(R.id.tv_coin_navigation_title);

        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnBackClickListener != null) {
                    mOnBackClickListener.callBack();
                }
            }
        });
        imageAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnCollectChangeListener == null) {
                    return;
                }
                CoinDetailResult.CoinTitleBean bean = mTitleList.get(currentIndex);
                mOnCollectChangeListener.onCollectChange(bean.id, bean.isCollected());
            }
        });
        imageLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex - 1 <= 0) {
                    return;
                }
                currentIndex--;
                if (mOnSelectChangeListener != null) {
                    mOnSelectChangeListener.onSelectChange(mTitleList.get(currentIndex).id);
                }
            }
        });
        imageRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex + 1 >= mTitleList.size() - 1) {
                    return;
                }
                currentIndex++;
                if (mOnSelectChangeListener != null) {
                    mOnSelectChangeListener.onSelectChange(mTitleList.get(currentIndex).id);
                }
            }
        });
        imageRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRefreshClickListener != null) {
                    mOnRefreshClickListener.refresh();
                }
            }
        });
    }

    public void setCoinTitleList(ArrayList<CoinDetailResult.CoinTitleBean> titleList, int currentIndex) {
        if (titleList != null && titleList.size() > 0) {
            this.mTitleList.clear();
            this.mTitleList.addAll(titleList);
            this.currentIndex = currentIndex;
            CoinDetailResult.CoinTitleBean bean = mTitleList.get(currentIndex);
            tvTitle.setText(bean.title);
            imageAdd.setImageResource(bean.isCollected() ? R.drawable.icon_navigation_delete : R.drawable.icon_navigation_add);
        }
    }

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.mOnBackClickListener = onBackClickListener;
    }

    public void setOnRefreshClickListener(OnRefreshClickListener onRefreshClickListener) {
        this.mOnRefreshClickListener = onRefreshClickListener;
    }

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.mOnSelectChangeListener = onSelectChangeListener;
    }

    public void setOnCollectChangeListener(onCollectChangeListener onCollectChangeListener) {
        this.mOnCollectChangeListener = onCollectChangeListener;
    }


    public interface OnBackClickListener {
        void callBack();
    }

    public interface OnRefreshClickListener {
        void refresh();
    }

    public interface OnSelectChangeListener {
        void onSelectChange(String coinId);
    }

    public interface onCollectChangeListener {
        void onCollectChange(String coinId, boolean isCollect);
    }
}
