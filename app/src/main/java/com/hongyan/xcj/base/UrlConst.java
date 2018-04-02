package com.hongyan.xcj.base;

public class UrlConst {

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
        return HOST + "Api/Users/articles.html";
    }

    /**
     * 添加收藏
     */
    public static String getCollectUrl() {
        return HOST + "Api/Users/collect.html";
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
        return HOST + "Api/Article/index.html";
    }

    /**
     * 首页->快讯
     */
    public static String getInfoNewsUrl() {
        return HOST + "Api/Article/newsflash.html";
    }

    /**
     * 首页->分析师说
     */
    public static String getInfoAnalysisUrl() {
        return HOST + "Api/Article/analyst.html";
    }

    /**
     * 首页->研报
     */
    public static String getInfoReportUrl() {
        return HOST + "Api/Article/projects.html";
    }

    /**
     * 币行情页面
     */
    public static String getCoinCoreUrl() {
        return HOST + "Api/Users/coinCore.html";
    }

    /**
     * 币行情页面
     */
    public static String getCoinDetailUrl() {
        return HOST + "Api/Digiccy/detail.html";
    }

    /**
     * 币行情页面
     */
    public static String getModifyNickName() {
        return HOST + "Api/Users/edit_nickname.html";
    }

}
