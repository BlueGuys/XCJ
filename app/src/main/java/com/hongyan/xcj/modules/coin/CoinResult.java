package com.hongyan.xcj.modules.coin;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.modules.coin.bean.KMAEntity;

import java.util.ArrayList;

public class CoinResult extends JPResult {

    public Data data;

    public static class Data {
        private ArrayList<KLineBean> coinHistory;
        private CoinCurrent coinCurrent;

        public ArrayList<String> getXValList() {
            ArrayList<String> xVal = new ArrayList<>();
            if (null != coinHistory && coinHistory.size() > 0) {
                for (KLineBean s : coinHistory) {
                    xVal.add(s.date);
                }
            }
            return xVal;
        }

        public ArrayList<CandleEntry> getCandleEntries() {
            ArrayList<CandleEntry> candleEntries = new ArrayList<>();
            if (null != coinHistory && coinHistory.size() > 0) {
                for (int i = 0; i < coinHistory.size(); i++) {
                    KLineBean k = coinHistory.get(i);
                    CandleEntry candleEntry = new CandleEntry(i, k.high, k.low, k.open, k.close);
                    candleEntries.add(candleEntry);
                }
            }
            return candleEntries;
        }

        public ArrayList<BarEntry> getBarEntries() {
            ArrayList<BarEntry> candleEntries = new ArrayList<>();
            if (null != coinHistory && coinHistory.size() > 0) {
                for (int i = 0; i < coinHistory.size(); i++) {
                    KLineBean k = coinHistory.get(i);
                    BarEntry barEntry = new BarEntry(i, k.high, k.low, k.open, k.close, k.vol);
                    candleEntries.add(barEntry);
                }
            }
            return candleEntries;
        }

        public ArrayList<Entry> getMa1DataL() {
            XCJKMAEntity kmaEntity = new XCJKMAEntity(coinHistory, 1);
            ArrayList<Entry> ma1DataL = new ArrayList<>();
            for (int i = 0; i < kmaEntity.getMAs().size(); i++) {
                ma1DataL.add(new Entry(kmaEntity.getMAs().get(i), i));
            }
            return ma1DataL;
        }

        public ArrayList<Entry> getMa5DataL() {
            XCJKMAEntity kmaEntity = new XCJKMAEntity(coinHistory, 5);
            ArrayList<Entry> ma5DataL = new ArrayList<>();
            for (int i = 0; i < kmaEntity.getMAs().size(); i++) {
                if (i >= 5) {
                    ma5DataL.add(new Entry(kmaEntity.getMAs().get(i), i));
                }
            }
            return ma5DataL;
        }

        public ArrayList<Entry> getMa10DataL() {
            XCJKMAEntity kmaEntity = new XCJKMAEntity(coinHistory, 10);
            ArrayList<Entry> ma10DataL = new ArrayList<>();
            for (int i = 0; i < kmaEntity.getMAs().size(); i++) {
                if (i >= 10) {
                    ma10DataL.add(new Entry(kmaEntity.getMAs().get(i), i));
                }
            }
            return ma10DataL;
        }


        public ArrayList<KLineBean> getCoinHistory() {
            return coinHistory;
        }

        public CoinCurrent getCoinCurrent() {
            return coinCurrent;
        }
    }

    public static class KLineBean {
        public String date;
        public float open;
        public float close;
        public float high;
        public float low;
        public float vol;
        public String rose;
    }

    public static class CoinCurrent {

        /**
         * 当前价格
         */
        public float price;
        /**
         * 今日涨跌值
         */
        public float changePrice;
        /**
         * 今日涨跌比率
         */
        public float changeRate;
        /**
         * 当前时间
         */
        public String timeStamp;
        /**
         * 开盘价
         */
        public String openPrice;
        /**
         * 收盘价
         */
        public String closePrice;
        /**
         * 最高价
         */
        public String highPrice;
        /**
         * 最低价
         */
        public String lowPrice;
        /**
         * 成交量
         */
        public String volume;
    }
}
