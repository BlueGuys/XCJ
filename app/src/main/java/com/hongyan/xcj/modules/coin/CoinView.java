package com.hongyan.xcj.modules.coin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.hongyan.xcj.R;


public class CoinView extends LinearLayout {

    private View view;
    protected XCJCombinedChart mChartKline;
    //X轴标签的类
    protected XAxis xAxisKline;
    //Y轴左侧的线
    protected YAxis axisLeftKline;
    //Y轴右侧的线
    protected YAxis axisRightKline;

    public CoinView(Context context) {
        super(context);
    }

    public CoinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = View.inflate(context, R.layout.view_coin, this);
        mChartKline = view.findViewById(R.id.kline_chart_k);
        mChartKline.setAutoScaleMinMaxEnabled(true);
        mChartKline.notifyDataSetChanged();
        mChartKline.invalidate();
        initChartKline();
    }

    /**
     * 初始化上面的chart公共属性
     */
    private void initChartKline() {
        mChartKline.setScaleEnabled(true);//启用图表缩放事件
        mChartKline.setDrawBorders(true);//是否绘制边线
        mChartKline.setBorderWidth(1);//边线宽度，单位dp
        mChartKline.setDragEnabled(true);//启用图表拖拽事件
        mChartKline.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartKline.setBorderColor(getResources().getColor(R.color.blue));//边线颜色
        mChartKline.setDescription("");//右下角对图表的描述信息
        mChartKline.setMinOffset(0f);
        mChartKline.setExtraOffsets(0f, 0f, 0f, 3f);

        Legend lineChartLegend = mChartKline.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        //bar x y轴
        xAxisKline = mChartKline.getXAxis();
        xAxisKline.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisKline.setTextColor(getResources().getColor(R.color.white));//设置字的颜色
        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mChartKline.getAxisLeft();
        axisLeftKline.setDrawGridLines(true);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(getResources().getColor(R.color.white));
//        axisLeftKline.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftKline.setSpaceTop(10);//距离顶部留白

        axisRightKline = mChartKline.getAxisRight();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);

        mChartKline.animateXY(2000, 2000);
    }

}
