package com.home.jms.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JMSResponse {

    public JMSResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @JsonProperty("responseMessage")
    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
