package com.tinqinacademy.hotel.restexport;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class ClientFactory {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
