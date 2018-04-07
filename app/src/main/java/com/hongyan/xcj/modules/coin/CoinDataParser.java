package com.hongyan.xcj.modules.coin;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.coin.bean.KMAEntity;

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
                combinedData.setData(getLineData(data));
                if (CoinDetailActivity.currentIndex != 0) {
                    combinedData.setData(getCandleData(data));
                }
                break;
            case TYPE_BAR:
                combinedData.setData(getBarData(data));
                combinedData.setData(getLineData2(data));
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
        set.setHighlightEnabled(false);
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
        set.setValueTextColor(mContext.getResources().getColor(R.color.white));
        return new CandleData(data.getXValList(), set);
    }

    private BarData getBarData(CoinResult.Data data) {
        BarDataSet set = new BarDataSet(data.getBarEntries(), "成交量");
        set.setBarSpacePercent(20); //bar空隙
        set.setHighlightEnabled(false);
        set.setHighLightAlpha(255);
        set.setHighLightColor(mContext.getResources().getColor(R.color.white));
        set.setDrawValues(false);

        List<Integer> list = new ArrayList<>();
        list.add(mContext.getResources().getColor(R.color.increasing_color));
        list.add(mContext.getResources().getColor(R.color.decreasing_color));
        set.setColors(list);
        return new BarData(data.getXValList(), set);
    }

    private LineData getLineData(CoinResult.Data data) {
        ArrayList<ILineDataSet> list = new ArrayList<>();
        list.add(setMaLine(1, data.getMa1DataL()));
        list.add(setMaLine(7, data.getMa7DataL()));
        list.add(setMaLine(30, data.getMa30DataL()));
        return new LineData(data.getXValList(), list);
    }

    private LineData getLineData2(CoinResult.Data data) {
        ArrayList<ILineDataSet> list = new ArrayList<>();
        list.add(setMaLine2(1, data.getMa1DataL()));
        list.add(setMaLine2(7, data.getMa7DataL()));
        list.add(setMaLine2(30, data.getMa30DataL()));
        return new LineData(data.getXValList(), list);
    }

    /**
     * @param ma
     * @param lineEntries
     * @return
     */
    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 7) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        if (CoinDetailActivity.currentIndex == 0) {
            if (ma == 1) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.white));
            } else {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.transparent));
            }
        } else {
            if (ma == 5) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.white));
            } else if (ma == 30) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.chart_line_orange));
            } else if (ma == 7) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.chart_line_blue));
            } else {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.transparent));
            }
        }
        lineDataSetMa.setDrawValues(false);
        lineDataSetMa.setLineWidth(0.5f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setHighlightEnabled(true);
        return lineDataSetMa;
    }

    /**
     * @param ma
     * @param lineEntries
     * @return
     */
    @NonNull
    private LineDataSet setMaLine2(int ma, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 7) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        if (CoinDetailActivity.currentIndex == 0) {
            if (ma == 1) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.white));
            } else {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.transparent));
            }
        } else {
            if (ma == 5) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.white));
            } else if (ma == 30) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.orange));
            } else if (ma == 7) {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.blue));
            } else {
                lineDataSetMa.setColor(mContext.getResources().getColor(R.color.transparent));
            }
        }
        lineDataSetMa.setColor(mContext.getResources().getColor(R.color.transparent));
        lineDataSetMa.setDrawValues(false);
        lineDataSetMa.setLineWidth(0f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setHighlightEnabled(true);
        return lineDataSetMa;
    }

}
