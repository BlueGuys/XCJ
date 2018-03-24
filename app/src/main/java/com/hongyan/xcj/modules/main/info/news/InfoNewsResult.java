package com.hongyan.xcj.modules.main.info.news;


import com.hongyan.xcj.base.JPResult;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class InfoNewsResult extends JPResult {

    public Data data;

    class Data {

        public ArrayList<News> list;

        public String hasMore;
    }

    public static class News {
        public String id;
        public String content;
        public String update_time;
    }
}
