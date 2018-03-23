package com.hongyan.xcj.modules.main;


import com.hongyan.xcj.base.JPResult;

/**
 * com.jp.choose.JPValidChannelResult
 *
 * @author wangning
 */
public class RegisterResult extends JPResult {

    public Data data;
    public AUC auc;

    class Data {
        /**
         * 动作序列等
         */
        public String result;
    }

    class AUC {
        public String eventID;
    }
}
