package com.hongyan.xcj.modules.main.info.analysis;


import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.utils.StringUtils;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class InfoAnalysisResult extends JPResult {

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

        public String is_collection;//0未收藏 1收藏

        public boolean isCollect() {
            return !StringUtils.isEmpty(is_collection) && "1".equals(is_collection);
        }

        public void setCollect(boolean collect) {
            is_collection = collect ? "1" : "0";
        }
    }
}
