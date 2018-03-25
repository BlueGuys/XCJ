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
    }

    public static class AD {
        public String id;
        public String title;
        public String photo;
        public String url;
    }

}
