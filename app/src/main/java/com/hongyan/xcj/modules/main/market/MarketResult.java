package com.hongyan.xcj.modules.main.market;


import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.utils.StringUtils;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class MarketResult extends JPResult {

    public Data data;

    class Data {

        public ArrayList<Market> marketList;

        public String hasMore;
    }

    class Market {
        public String id;
        public String name;
        public String logo;
        /**
         * 今日成交量
         */
        public String volume;
        /**
         * 今日成交量
         */
        public String amount;
        /**
         * rmb价格
         */
        public String price;
        /**
         * 美元价格
         */
        public String dollar_price;
        /**
         * 0  上涨  1 下跌
         */
        public String change;
        /**
         * 涨跌百分比
         */
        public String chg;
        /**
         * 描述
         */
        public String desc;

        public boolean isUp() {
            return !StringUtils.isEmpty(change) && "0".equals(change);
        }
    }
}
