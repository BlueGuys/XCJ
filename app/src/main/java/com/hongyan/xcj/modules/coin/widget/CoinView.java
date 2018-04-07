package com.hongyan.xcj.modules.coin.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.coin.CoinDataParser;
import com.hongyan.xcj.modules.coin.CoinResult;
import com.hongyan.xcj.modules.coin.mychart.CoupleChartGestureListener;
import com.hongyan.xcj.modules.coin.mychart.MyCombinedChart;
import com.hongyan.xcj.modules.event.ChartMessageEvent;
import com.hongyan.xcj.utils.JavaTypesHelper;
import com.hongyan.xcj.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CoinView extends LinearLayout {

    private View view;
    private CoinViewHeader mHeader;
    private ChartCollectView mCollectView;
    private TextView tvCoinTime, tvCoinOpen, tvCoinHigh, tvCoinLow, tvCoinClose, tvCoinUp, tvCoinChange;
    protected MyCombinedChart mChartKline, mChartVolume;
    //X轴标签的类
    protected XAxis xAxisKline, xAxisVolume;
    //Y轴左侧的线
    protected YAxis axisLeftKline, axisLeftVolume;
    //Y轴右侧的线
    protected YAxis axisRightKline, axisRightVolume;

    private CoinDataParser mParser;
    private ProgressBar progressBar;

    private ArrayList<CoinResult.KLineBean> coinHistory;

    public CoinView(Context context) {
        super(context);
    }

    public CoinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mParser = new CoinDataParser(context);
        view = View.inflate(context, R.layout.view_coin, this);
        mHeader = view.findViewById(R.id.coin_view_header);
        mCollectView = view.findViewById(R.id.coin_view_collect);
        mChartKline = view.findViewById(R.id.coin_detail_chartK);
        mChartVolume = view.findViewById(R.id.coin_detail_volume);
        progressBar = view.findViewById(R.id.coin_progress);
        initChartKline();
        initChartVolume();
        initCurrentCoinView();
        mCollectView.setOnSelectChangeListener(new ChartCollectView.OnSelectChangeListener() {
            @Override
            public void change(int index) {
                ChartMessageEvent messageEvent = new ChartMessageEvent();
                messageEvent.setId(index);
                EventBus.getDefault().post(messageEvent);
            }
        });
    }

    private void initCurrentCoinView() {
        tvCoinTime = view.findViewById(R.id.tv_chart_time);
        tvCoinOpen = view.findViewById(R.id.tv_chart_open);
        tvCoinHigh = view.findViewById(R.id.tv_chart_high);
        tvCoinLow = view.findViewById(R.id.tv_chart_low);
        tvCoinClose = view.findViewById(R.id.tv_chart_close);
        tvCoinUp = view.findViewById(R.id.tv_chart_up);
        tvCoinChange = view.findViewById(R.id.tv_chart_change);
    }

    public void updateData(CoinResult.Data data) {
        mHeader.update(mParser.getCoinCurrent(data));
        coinHistory = data.getCoinHistory();
        invalidateEntry(coinHistory.size() - 1);
        mChartKline.setData(mParser.getCombinedData(data, CoinDataParser.TYPE_CANDLE));
        mChartVolume.setData(mParser.getCombinedData(data, CoinDataParser.TYPE_BAR));
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 渲染横条数据
     */
    private void invalidateEntry(int index) {
        if (index < 0 || index > coinHistory.size() - 1) {
            return;
        }
        CoinResult.KLineBean bean = this.coinHistory.get(index);
        tvCoinTime.setText(bean.date);
        tvCoinOpen.setText("开：" + bean.open);
        tvCoinClose.setText("收：" + bean.close);
        tvCoinHigh.setText("高：" + bean.high);
        tvCoinLow.setText("低：" + bean.low);

        //涨幅
        if (StringUtils.isEmpty(bean.rose)) {
            tvCoinUp.setText("0%");
        } else {
            tvCoinUp.setText(bean.rose + "%");
            if (JavaTypesHelper.toFloat(bean.rose) > 0) {
                tvCoinUp.setTextColor(Color.RED);
            } else {
                tvCoinUp.setTextColor(getResources().getColor(R.color.text_color_green));
            }
        }

        //振幅
        float change = (bean.high - bean.low) / bean.high * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        tvCoinChange.setText(decimalFormat.format(change) + "%");
    }

    private Highlight mHighLight;

    /**
     * 初始化上面的chart公共属性
     */
    private void initChartKline() {
        mChartKline.setScaleEnabled(true);//启用图表缩放事件
        mChartKline.setDrawBorders(false);//是否绘制边线
        mChartKline.setBorderWidth(1);//边线宽度，单位dp
        mChartKline.setDragEnabled(true);//启用图表拖拽事件
        mChartKline.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartKline.setBorderColor(getResources().getColor(R.color.xcj_line));//边线颜色
        mChartKline.setDescription("");//右下角对图表的描述信息
        mChartKline.setMinOffset(0f);
        mChartKline.setExtraOffsets(0f, 0f, 0f, 3f);
        mChartKline.setTop(20);

        Legend lineChartLegend = mChartKline.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        //bar x y轴
        xAxisKline = mChartKline.getXAxis();
        xAxisKline.setDrawLabels(false); //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisKline.setTextColor(getResources().getColor(R.color.xcj_text_b));//设置字的颜色
        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mChartKline.getAxisLeft();
        axisLeftKline.setDrawGridLines(false);
        axisLeftKline.setDrawAxisLine(true);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(false);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(getResources().getColor(R.color.xcj_text_b));
//        axisLeftKline.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftKline.setSpaceTop(10);//距离顶部留白

        axisRightKline = mChartKline.getAxisRight();
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(true);
        axisRightKline.setDrawLabels(true);
        axisRightKline.setMinWidth(50);
        axisRightKline.setMaxWidth(50);
        axisRightKline.enableGridDashedLine(10f, 10f, 0f);
        axisRightKline.setTextColor(getResources().getColor(R.color.xcj_text_b));
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);

        mChartKline.animateXY(2000, 2000);

        mChartKline.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (h == null) {
                    return;
                }
                mHighLight = h;
                mChartVolume.highlightValues(new Highlight[]{mHighLight});
                mChartKline.highlightValues(new Highlight[]{mHighLight});
                invalidateEntry(e.getXIndex());
            }

            @Override
            public void onNothingSelected() {
                mChartVolume.highlightValues(new Highlight[]{mHighLight});
                mChartKline.highlightValues(new Highlight[]{mHighLight});
            }
        });
        // 将交易量控件的滑动事件传递给K线控件
        mChartKline.setOnChartGestureListener(new CoupleChartGestureListener(mChartKline, new Chart[]{mChartVolume}) {
            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                mHighLight = mChartKline.getHighlightByTouchPoint(me.getX(), me.getY());
                if (mHighLight == null) {
                    return;
                }
                mChartVolume.highlightValues(new Highlight[]{mHighLight});
                mChartKline.highlightValues(new Highlight[]{mHighLight});
                invalidateEntry(mHighLight.getXIndex());
            }
        });

    }

    /**
     * 初始化下面的chart公共属性
     */
    private void initChartVolume() {
        mChartVolume.setDrawBorders(false);  //边框是否显示
        mChartVolume.setBorderWidth(1);//边框的宽度，float类型，dp单位
        mChartVolume.setBorderColor(getResources().getColor(R.color.xcj_line));//边框颜色
        mChartVolume.setDescription(""); //图表默认右下方的描述，参数是String对象
        mChartVolume.setDragEnabled(true);// 是否可以拖拽
        mChartVolume.setScaleYEnabled(false); //是否可以缩放 仅y轴
        mChartVolume.setMinOffset(3f);
        mChartVolume.setExtraOffsets(0f, 0f, 0f, 5f);

        Legend lineChartLegend = mChartVolume.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        //bar x y轴
        xAxisVolume = mChartVolume.getXAxis();
        xAxisVolume.setEnabled(true);
        xAxisVolume.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisVolume.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisVolume.setDrawAxisLine(true); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisVolume.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisVolume.setTextColor(getResources().getColor(R.color.xcj_text_b));//设置字的颜色
        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisVolume.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftVolume = mChartVolume.getAxisLeft();
        axisLeftVolume.setDrawGridLines(false);
        axisLeftVolume.setDrawAxisLine(true);
        axisLeftVolume.setDrawZeroLine(true);
        axisLeftVolume.setDrawLabels(false);
        axisLeftVolume.enableGridDashedLine(10f, 10f, 0f);
        axisLeftVolume.setTextColor(getResources().getColor(R.color.xcj_text_b));
        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftVolume.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftVolume.setSpaceTop(10);//距离顶部留白

        axisRightVolume = mChartVolume.getAxisRight();
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setDrawAxisLine(true);
        axisRightVolume.setDrawLabels(true);
        axisRightVolume.setMinWidth(50);
        axisRightVolume.setMaxWidth(50);
        axisRightVolume.enableGridDashedLine(10f, 10f, 0f);
        axisRightVolume.setTextColor(getResources().getColor(R.color.xcj_text_b));
        axisRightVolume.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        mChartVolume.setDragDecelerationEnabled(true);
        mChartVolume.setDragDecelerationFrictionCoef(0.2f);

        mChartVolume.animateXY(2000, 2000);

        mChartVolume.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (h == null) {
                    return;
                }
                mHighLight = h;
                mChartVolume.highlightValues(new Highlight[]{mHighLight});
                mChartKline.highlightValues(new Highlight[]{mHighLight});
                invalidateEntry(e.getXIndex());
            }

            @Override
            public void onNothingSelected() {
                mChartVolume.highlightValues(new Highlight[]{mHighLight});
                mChartKline.highlightValues(new Highlight[]{mHighLight});
            }
        });
        // 将交易量控件的滑动事件传递给K线控件
        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume, new Chart[]{mChartKline}) {
            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                mHighLight = mChartVolume.getHighlightByTouchPoint(me.getX(), me.getY());
                if (mHighLight == null) {
                    return;
                }
                mChartVolume.highlightValues(new Highlight[]{mHighLight});
                mChartKline.highlightValues(new Highlight[]{mHighLight});
                invalidateEntry(mHighLight.getXIndex());
            }
        });
    }

}
