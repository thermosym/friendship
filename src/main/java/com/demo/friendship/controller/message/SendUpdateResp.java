package com.demo.friendship.controller.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class SendUpdateResp extends BaseResp {

    private List<String> recipients;

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("recipients", recipients)
                .append("success", success)
                .toString();
    }
}
