package com.home.jms.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JMSRequest {

    @JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
