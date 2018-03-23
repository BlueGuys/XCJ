package com.hongyan.xcj.base;


import org.json.JSONObject;

public class Error  {

    public static final Error NETWORK_ERROR = new Error();
    public static final Error DATA_PARSED_ERROR = new Error();

    static {
        NETWORK_ERROR.returnCode =   "-100";
        NETWORK_ERROR.returnMessage = "network failed.";
        NETWORK_ERROR.returnUserMessage = "网络连接异常";

        DATA_PARSED_ERROR.returnCode = "-200;";
        DATA_PARSED_ERROR.returnMessage = "data parse failed.";
        DATA_PARSED_ERROR.returnUserMessage = "数据解析错误，请重试。";
    }

    private String returnCode;
    private String returnMessage;
    private String returnUserMessage;
    private String detailMessage;

    public void parseJson(JSONObject json) {
        if (json == null) {
            return;
        }

        this.returnCode = json.optString("returnCode");
        this.returnMessage = json.optString("returnMessage");
        this.returnUserMessage = json.optString("returnUserMessage");
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnUserMessage() {
        return returnUserMessage;
    }

    public void setReturnUserMessage(String returnUserMessage) {
        this.returnUserMessage = returnUserMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
