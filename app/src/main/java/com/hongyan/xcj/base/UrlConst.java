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
        return HOST + "Api/News/index.html";
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

    /**
     * 我的收藏
     */
    public static String getMyCollectUrl() {
        return HOST + "http://www.xicaijing.com/Api/Users/articles.html";
    }

    /**
     * 添加收藏
     */
    public static String getCollectUrl() {
        return HOST + "Api/Users/articles.html";
    }

    /**
     * 取消收藏
     */
    public static String getCancelCollectUrl() {
        return HOST + "Api/Users/cancel_collect.html";
    }

    /**
     * 首页->推荐
     */
    public static String getInfoRecommendUrl() {
        return HOST + "Api/Report/index.html";
    }

    /**
     * 首页->快讯
     */
    public static String getInfoNewsUrl() {
        return HOST + "Api/Report/newsflash.html";
    }

    /**
     * 首页->分析师说
     */
    public static String getInfoAnalysisUrl() {
        return HOST + "Api/Report/analyst.html";
    }

    /**
     * 首页->研报
     */
    public static String getInfoReportUrl() {
        return HOST + "Api/Report/projects.html";
    }

}
