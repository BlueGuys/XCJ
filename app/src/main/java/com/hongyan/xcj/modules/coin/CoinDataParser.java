package com.hongyan.xcj.modules.coin;

import android.content.Context;
import android.graphics.Paint;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CombinedData;
import com.hongyan.xcj.R;

import java.util.ArrayList;
import java.util.List;

public class CoinDataParser {

    public static final int TYPE_CANDLE = 0;
    public static final int TYPE_BAR = 1;
    private Context mContext;

    public CoinDataParser(Context context) {
        this.mContext = context;
    }

    public CombinedData getCombinedData(CoinResult.Data data, int type) {
        CombinedData combinedData = new CombinedData(data.getXValList());
        switch (type) {
            case TYPE_CANDLE:
                combinedData.setData(getCandleData(data));
                break;
            case TYPE_BAR:
                combinedData.setData(getCandleData(data));
                combinedData.setData(getBarData(data));
                break;
        }
        return combinedData;
    }

    public CoinResult.CoinCurrent getCoinCurrent(CoinResult.Data data) {
        if (data == null) {
            return null;
        }
        return data.getCoinCurrent();
    }

    private CandleData getCandleData(CoinResult.Data data) {
        CandleDataSet set = new CandleDataSet(data.getCandleEntries(), "");
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(1f);
        set.setValueTextSize(10f);
        set.setDecreasingColor(mContext.getResources().getColor(R.color.decreasing_color));//设置开盘价高于收盘价的颜色
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(mContext.getResources().getColor(R.color.increasing_color));//设置开盘价地狱收盘价的颜色
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(mContext.getResources().getColor(R.color.decreasing_color));//设置开盘价等于收盘价的颜色
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(1f);
        set.setHighLightColor(mContext.getResources().getColor(R.color.white));
        set.setDrawValues(true);
        set.setValueTextColor(mContext.getResources().getColor(R.color.marker_text_bg));
        return new CandleData(data.getXValList(), set);
    }


    private BarData getBarData(CoinResult.Data data) {
        BarDataSet set = new BarDataSet(data.getBarEntries(), "成交量");
        set.setBarSpacePercent(20); //bar空隙
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(mContext.getResources().getColor(R.color.white));
        set.setDrawValues(false);

        List<Integer> list = new ArrayList<>();
        list.add(mContext.getResources().getColor(R.color.increasing_color));
        list.add(mContext.getResources().getColor(R.color.decreasing_color));
        set.setColors(list);
        return new BarData(data.getXValList(), set);
    }

}
