package com.hongyan.xcj.modules.main.info.reports;


import com.hongyan.xcj.base.JPResult;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class InfoReportResult extends JPResult {

    public Data data;

    class Data {

        public ArrayList<Report> list;

        public String hasMore;
    }

    public static class Report {
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

        public boolean isCollect;

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }
    }
}
