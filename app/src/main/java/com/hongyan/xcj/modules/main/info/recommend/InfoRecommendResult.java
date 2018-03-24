package com.hongyan.xcj.modules.main.info.recommend;


import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.utils.StringUtils;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class InfoRecommendResult extends JPResult {

    public Data data;

    class Data {

        public ArrayList<Article> list;

        public String hasMore;
    }

    public static class Article {
        public String id;
        public String title;
        public String source;
        /**
         * 今日成交量
         */
        public String photo;
        /**
         * 今日成交量
         */
        public String update_time;
        /**
         * rmb价格
         */
        public String url;
    }
}
