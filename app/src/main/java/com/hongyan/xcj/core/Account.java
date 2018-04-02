package com.hongyan.xcj.core;

import com.hongyan.xcj.base.JPResult;

public class Account extends JPResult {

    public Data data;

    class Data {
        public String id;
        public String email;
        public String status;
        public String nickname;
        public String mobile;
    }

}
