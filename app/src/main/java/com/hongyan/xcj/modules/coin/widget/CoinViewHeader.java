package com.hongyan.xcj.modules.coin.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.coin.CoinResult;
import com.hongyan.xcj.utils.DateUtils;
import com.hongyan.xcj.utils.JavaTypesHelper;

import java.util.Date;

public class CoinViewHeader extends LinearLayout {

    private TextView tvPrice;
    private TextView tvOpenPrice;
    private TextView tvClosePrice;
    private TextView tvHighPrice;
    private TextView tvLowPrice;
    private TextView tvChangePrice;
    private TextView tvChangeRate;
    private TextView tvTimeDate;
    private TextView tvTimeHour;
    private TextView tvVolume;

    public CoinViewHeader(Context context) {
        super(context);
    }

    public CoinViewHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_coin_header, this);
        tvPrice = view.findViewById(R.id.tv_price);
        tvOpenPrice = view.findViewById(R.id.tv_open_price);
        tvClosePrice = view.findViewById(R.id.tv_close_price);
        tvHighPrice = view.findViewById(R.id.tv_high_price);
        tvLowPrice = view.findViewById(R.id.tv_low_price);
        tvChangePrice = view.findViewById(R.id.tv_change_price);
        tvChangeRate = view.findViewById(R.id.tv_change_rate);
        tvTimeDate = view.findViewById(R.id.tv_time_date);
        tvTimeHour = view.findViewById(R.id.tv_time_hour);
        tvVolume = view.findViewById(R.id.tv_volume);
    }

    public void update(CoinResult.CoinCurrent data) {
        if (data == null) {
            return;
        }
        Date date = new Date(JavaTypesHelper.toLong(data.timeStamp+"000"));
        String day = DateUtils.formatDate(date, DateUtils.MMDD);
        String hour = DateUtils.formatDate(date, DateUtils.HHmmss);
        tvPrice.setText(String.valueOf(data.price));
        tvOpenPrice.setText("开盘：" + String.valueOf(data.openPrice));
        tvClosePrice.setText("收盘：" + String.valueOf(data.closePrice));
        tvHighPrice.setText("最高：" + String.valueOf(data.highPrice));
        tvLowPrice.setText("最低：" + String.valueOf(data.lowPrice));
        tvChangePrice.setText(String.valueOf(data.changePrice));
        tvChangeRate.setText(String.valueOf(data.changeRate + "%"));
        tvChangeRate.setTextColor(data.changeRate >= 0f ? getResources().getColor(R.color.increasing_color) : getResources().getColor(R.color.decreasing_color));
        tvChangePrice.setTextColor(data.changePrice >= 0f ? getResources().getColor(R.color.increasing_color) : getResources().getColor(R.color.decreasing_color));
        tvTimeDate.setText(day);
        tvTimeHour.setText(hour);
        tvVolume.setText("成交量：" + String.valueOf(data.volume));
    }
}
