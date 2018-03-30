package com.hongyan.xcj.modules.coin;

import com.hongyan.xcj.base.JPResult;
import com.hongyan.xcj.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by wangning on 2018/3/30.
 */

public class CoinDetailResult extends JPResult {

    public Data data;

    public static class Data {
        public ArrayList<CoinTitleBean> titleList;
    }

    public static class CoinTitleBean {
        public String title;
        public String id;
        public String isCollected;//0未收藏 1收藏

        public boolean isCollected() {
            return !StringUtils.isEmpty(isCollected) && isCollected.equals("1");
        }
    }

}
