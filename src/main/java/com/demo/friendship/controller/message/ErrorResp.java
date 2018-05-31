package com.demo.friendship.controller.message;

public class ErrorResp extends BaseResp {
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ErrorResp() {}

    public ErrorResp(String errorMsg) {
        this.success = false;
        this.errorMsg = errorMsg;
    }
}
