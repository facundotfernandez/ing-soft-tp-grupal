package com.tpIngSoft1.restApi.config;

import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendgridConfig {

    @Bean
    public SendGrid getSendgrid() {
        return new SendGrid("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");
    }

}