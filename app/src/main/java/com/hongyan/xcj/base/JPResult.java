package com.hongyan.xcj.base;

/**
 * com.jp.base.JPResult
 *
 */
public class JPResult {

    protected Error error;

    public Error getError() {
        if (this.error == null) {
            setToDataParsedError();
        }
        return this.error;
    }

    public void setError(Error e) {
        this.error = e;
    }

    public void setToNetworkError() {
        this.error = Error.NETWORK_ERROR;
        this.error.setReturnUserMessage("网络连接失败");
    }

    public void setToDataParsedError() {
        this.error = Error.DATA_PARSED_ERROR;
    }

    public String getReturnUserMessage() {
        if (error != null) {
            return error.getReturnUserMessage();
        } else {
            return Error.DATA_PARSED_ERROR.getReturnUserMessage();
        }
    }

    public String getReturnMessage() {
        if (error != null) {
            return error.getReturnMessage();
        } else {
            return Error.DATA_PARSED_ERROR.getReturnMessage();
        }
    }

    public String getReturnCode() {
        String ret = "";
        if (error != null) {
            ret = error.getReturnCode();
        }
        return ret;
    }

    public boolean isSuccessful() {
        String returnCode = String.valueOf(getReturnCode());
        return "10000".equals(returnCode);
    }
}
