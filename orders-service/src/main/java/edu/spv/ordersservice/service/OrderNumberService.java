package edu.spv.ordersservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderNumberService {

    private final WebClient webClient;

    /**
     * Обращается к сервису генерации номера заказа за уникальным номером заказа
     * @return уникальный номер заказа
     */
    public String getOrderNumber() {
        try {
            return webClient.get()
                    .uri("/numbers")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order number from number-generate-service", e);
        }
    }
}
