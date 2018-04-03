package com.hongyan.xcj.modules.search;


import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.utils.StringUtils;

import java.util.ArrayList;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class SearchResult extends JPResult {

    public Data data;

    class Data {
        private String isExpectData;//0 未查询到有效数据，推荐数据  1 数据是搜到的
        public ArrayList<CoinBean> coinList;
        public ArrayList<ArticleBean> articleList;

        public boolean isVaild() {
            return !StringUtils.isEmpty(isExpectData) && "1".equals(isExpectData);
        }
    }

    static class CoinBean {
        public String id;
        public String logo;
        public String name;
        public String url;
    }

    static class ArticleBean {
        public String title;
        public String url;
    }
}
