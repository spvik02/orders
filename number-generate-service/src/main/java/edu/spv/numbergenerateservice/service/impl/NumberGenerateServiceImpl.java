package edu.spv.numbergenerateservice.service.impl;

import edu.spv.numbergenerateservice.service.NumberGenerateService;
import edu.spv.numbergenerateservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NumberGenerateServiceImpl implements NumberGenerateService {

    private final RedisService redisService;

    /**
     * Generates unique order number and saves it in Redis
     *
     * @return generated order number
     */
    @Override
    public String generateUniqueNumber() {

        String orderNumber;
        do {
            orderNumber = generateRandomNumber();
        } while (!redisService.saveIfAbsent(orderNumber));

        return orderNumber;
    }

    /**
     * Generates random order number from random 5-digit number and current date in the yyyyMMdd format
     *
     * @return formatted String with generated number
     */
    private String generateRandomNumber() {
        int randomNum = (int) (Math.random() * 100000);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("%05d%s", randomNum, date);
    }
}
