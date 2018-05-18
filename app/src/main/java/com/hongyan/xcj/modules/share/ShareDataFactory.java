package com.hongyan.xcj.modules.share;

import com.hongyan.xcj.R;

import java.util.ArrayList;

/**
 * Created by wangning on 2018/4/10.
 */

public class ShareDataFactory {

    public static ArrayList<ShareChannel> getShareChannelList() {
        ArrayList<ShareChannel> list = new ArrayList<>();
        list.add(new ShareChannel(0, R.drawable.icon_share_we_chat, "微信"));
        list.add(new ShareChannel(1, R.drawable.icon_share_weibo, "微博"));
        list.add(new ShareChannel(2, R.drawable.icon_share_wechat_moment, "朋友圈"));
        list.add(new ShareChannel(3, R.drawable.icon_share_qq, "QQ"));
        list.add(new ShareChannel(4, R.drawable.icon_share_qq_zone, "QQ空间"));
        return list;
    }

}
