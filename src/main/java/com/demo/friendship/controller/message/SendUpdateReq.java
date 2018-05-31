package com.demo.friendship.controller.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class SendUpdateReq {
    @NotBlank
    private String sender;
    @NotBlank
    private String text;

    public SendUpdateReq() {
    }

    public SendUpdateReq(@NotBlank String sender, @NotBlank String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sender", sender)
                .append("text", text)
                .toString();
    }
}
