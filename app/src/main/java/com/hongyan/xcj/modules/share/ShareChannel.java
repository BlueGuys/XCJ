package com.hongyan.xcj.modules.share;

/**
 * Created by wangning on 2018/4/10.
 */

public class ShareChannel {

    /**
     * 渠道ID
     */
    private int channelId;

    /**
     * 渠道图片
     */
    private int drawable;

    /**
     * 渠道文案
     */
    private String label;

    public ShareChannel(int channelId, int drawable, String label) {
        this.channelId = channelId;
        this.drawable = drawable;
        this.label = label;
    }

    public int getChannelId() {
        return channelId;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getLabel() {
        return label;
    }
}
