package com.demo.friendship.controller.message;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseResp {
    protected boolean success;

    private static final BaseResp ok = new BaseResp(true);

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BaseResp() {
    }

    public BaseResp(boolean success) {
        this.success = success;
    }

    public static BaseResp ok() {
        return ok;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
