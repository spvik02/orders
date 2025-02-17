package edu.spv.ordersservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${number-generate-service.base-url:http://localhost:8081}")
    private String numberGenerateServiceBaseUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(numberGenerateServiceBaseUrl).build();
    }
}
