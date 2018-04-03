package com.hongyan.xcj.modules.event;

/**
 * Created by wangning on 2018/3/21.
 */

public class ChartMessageEvent {

    private int id = -1;
    private int isCollectSuccessful = -1;// -1 无效，初始值  0 取消成功  1收藏成功

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsCollectSuccessful() {
        return isCollectSuccessful;
    }

    public void setIsCollectSuccessful(int isCollectSuccessful) {
        this.isCollectSuccessful = isCollectSuccessful;
    }
}
