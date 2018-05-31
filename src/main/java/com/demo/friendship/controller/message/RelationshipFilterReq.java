package com.demo.friendship.controller.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class RelationshipFilterReq {
    @NotBlank
    private String requestor;
    @NotBlank
    private String target;

    public RelationshipFilterReq() {
    }

    public RelationshipFilterReq(String requestor, String target) {
        this.requestor = requestor;
        this.target = target;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("requestor", requestor)
                .append("target", target)
                .toString();
    }
}
