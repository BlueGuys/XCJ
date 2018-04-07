package com.hongyan.xcj.core;


import com.hongyan.xcj.base.JPResult;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class ShareResult extends JPResult {

    public Data data;

    class Data {
        public ShareInfo share_info;
    }

    static class ShareInfo {
        public String title;
        public String photo;
        public String url;
        public String note;
    }
}
