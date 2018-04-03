package com.hongyan.xcj.modules.event;

/**
 * Created by wangning on 2018/3/21.
 */

public class CollectMessageEvent {

    private String id;
    private String type;
    private String action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
