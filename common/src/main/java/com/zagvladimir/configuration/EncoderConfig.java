package com.zagvladimir.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class EncoderConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoderService() {
        return  new BCryptPasswordEncoder();
    }
}
