package com.hongyan.xcj.base;

public class UrlConst {

    //http://www.xicaijing.com/Api/Article/index.html?p=1&pagesize=5&sign=59503d3f26812124a5f36084d6f44a49&timestamp=1521336816

    private static String HOST = "http://www.xicaijing.com/";

    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String HOST) {
        UrlConst.HOST = HOST;
    }

    /**
     * 鉴权注册接口
     */
    public static String getInfoList() {
        return HOST + "Api/Article/index.html";
    }

    /**
     * 行情全部页面接口
     */
    public static String getMarketList() {
        return HOST + "Api/Digiccy/lists.html";
    }

    /**
     * 获取用户信息
     */
    public static String getAccountInfo() {
        return HOST + "Api/Users/user_info.html";
    }
}
