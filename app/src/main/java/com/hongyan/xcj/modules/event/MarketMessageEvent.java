package com.hongyan.xcj.modules.event;

/**
 * Created by wangning on 2018/3/21.
 */

public class MarketMessageEvent {

    public int frgmentID;

    public MarketMessageEvent(int frgmentID) {
        this.frgmentID = frgmentID;
    }

    public int getFrgmentID() {
        return frgmentID;
    }

    public void setFrgmentID(int frgmentID) {
        this.frgmentID = frgmentID;
    }
}
