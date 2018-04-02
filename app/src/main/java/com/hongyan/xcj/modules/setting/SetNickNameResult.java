package com.hongyan.xcj.modules.setting;


import com.hongyan.xcj.base.JPResult;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class SetNickNameResult extends JPResult {

    public Data data;

    class Data {

        public ArrayList<Collection> collectionList;

        public String hasMore;
    }

    static class Collection {
        public String id;
        public String title;
        public String source;
        public String photo;
        public String update_time;
        public String url;
    }
}
