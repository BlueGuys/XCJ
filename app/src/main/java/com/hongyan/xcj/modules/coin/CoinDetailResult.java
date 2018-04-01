package com.hongyan.xcj.modules.coin;

import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.utils.StringUtils;

import java.util.ArrayList;

public class CoinDetailResult extends JPResult {

    public Data data;

    public static class Data {
        public String buyRate;
        public String sellRate;

        public ArrayList<CoinTitleBean> titleList;
        /**
         * 买卖10档数据列表
         */
        public ArrayList<MMBean> mmList;

        /**
         * 交易列表
         */
        public ArrayList<DealBean> dealList;

        public CoinDetailBean coinDetail;

        /**
         * 买卖10档数据
         */
        public ArrayList<Info> infoList;

    }

    public static class CoinTitleBean {
        public String title;
        public String id;
        public String isCollected;//0未收藏 1收藏

        public boolean isCollected() {
            return !StringUtils.isEmpty(isCollected) && isCollected.equals("1");
        }
    }

    /**
     * 买卖明细Item
     */
    public static class MMBean {
        public String buyAmount;
        public String buyCount;
        public String sellAmount;
        public String sellCount;
    }

    /**
     * 交易明细Item
     */
    public static class DealBean {
        public String timeStamp;
        public String price;
        public String volume;
        private String isUp;//0 下跌  1上涨

        public boolean isUp() {
            return !StringUtils.isEmpty(isUp) && "1".equals(isUp);
        }
    }

    /**
     * 币种详情
     */
    public static class CoinDetailBean {
        /**
         * 简介
         */
        public String brief;
        public String CNName;
        public String ENName;
        public String webSiteName;
        public String blockName;
        /**
         * 交易所
         */
        public String exchangeName;
        public String releaseTime;
        public String whitePaper;
    }

    /**
     * 资讯Item
     */
    public static class Info {
        public String title;
        public String timeStamp;
        public String url;
    }


}
