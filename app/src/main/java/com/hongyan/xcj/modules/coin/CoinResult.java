package com.hongyan.xcj.modules.coin;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.hongyan.xcj.base.JPResult;

import java.util.ArrayList;

public class CoinResult extends JPResult {


    public Data data;

    public static class Data {
        private ArrayList<KLineBean> day;

        public ArrayList<String> getXVals() {
            ArrayList<String> xVals = new ArrayList<>();
            if (null != day && day.size() > 0) {
                for (KLineBean s : day) {
                    xVals.add(s.date);
                }
            }
            return xVals;
        }

        public ArrayList<CandleEntry> getCandleEntries() {
            ArrayList<CandleEntry> candleEntries = new ArrayList<>();
            if (null != day && day.size() > 0) {
                for (int i = 0; i < day.size(); i++) {
                    KLineBean k = day.get(i);
                    CandleEntry candleEntry = new CandleEntry(i, k.high, k.low, k.open, k.close);
                    candleEntries.add(candleEntry);
                }
            }
            return candleEntries;
        }

        public ArrayList<BarEntry> getBarEntries() {
            ArrayList<BarEntry> candleEntries = new ArrayList<>();
            if (null != day && day.size() > 0) {
                for (int i = 0; i < day.size(); i++) {
                    KLineBean k = day.get(i);
                    BarEntry barEntry = new BarEntry(i, k.high, k.low, k.open, k.close, k.vol);
                    candleEntries.add(barEntry);
                }
            }
            return candleEntries;
        }
    }

    public static class KLineBean {
        public String date;
        public float open;
        public float close;
        public float high;
        public float low;
        public float vol;
    }
}
