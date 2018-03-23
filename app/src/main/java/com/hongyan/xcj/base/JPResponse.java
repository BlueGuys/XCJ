package com.hongyan.xcj.base;

/**
 * com.jp.base.JPResponse
 *
 * @author wangning
 * @version 2.1.0
 * @date 2017/7/6:15:24
 * @desc
 */
public class JPResponse {

    private String mResponse;

    private JPResult mResult;

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        this.mResponse = response;
    }

    public JPResult getResult() {
        return mResult;
    }

    public void setResult(JPResult result) {
        this.mResult = result;
    }
}
