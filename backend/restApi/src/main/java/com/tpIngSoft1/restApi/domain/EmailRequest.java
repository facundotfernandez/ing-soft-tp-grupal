package com.tpIngSoft1.restApi.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRequest {

    private String to;
    private String subject;
    private String body;

    public EmailRequest(String to, String subject, String body) {
        super();
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
