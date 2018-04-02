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
        public ArrayList<AD> ad;
        public String hasMore;
    }

    public static class Article {
        public String id;
        public String title;
        public String source;
        public String photo;
        public String update_time;
        public String url;
        public String is_collection;//0未收藏 1收藏

        public boolean isCollect() {
            return !StringUtils.isEmpty(is_collection) && "1".equals(is_collection);
        }

        public void setCollect(boolean collect) {
            is_collection = collect ? "1" : "0";
        }
    }

    public static class AD {
        public String id;
        public String title;
        public String photo;
        public String url;
    }

}
