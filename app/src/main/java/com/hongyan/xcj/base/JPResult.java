package com.hongyan.xcj.base;

/**
 * com.jp.base.JPResult
 */
public class JPResult {

    protected Error error;

    public static class Error {
        public String errorCode;
        public String errorMessage;
    }

    public String getReturnCode() {
        String ret = "";
        if (error != null) {
            ret = error.errorCode;
        }
        return ret;
    }

    public String getReturnMessage() {
        String ret = "";
        if (error != null) {
            ret = error.errorMessage;
        }
        return ret;
    }

    public boolean isSuccessful() {
        String returnCode = String.valueOf(getReturnCode());
        return "10000".equals(returnCode);
    }
}
