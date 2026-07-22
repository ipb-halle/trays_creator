package com.location.creator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class ClientConfig {

    @Bean
    @Primary
    RestClient signalsRestClient(SignalsTrialProperties properties) {

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("X-API-Key", properties.getApiKey())
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.api+json")
                .build();
    }


}
