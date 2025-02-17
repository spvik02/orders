package edu.spv.numbergenerateservice.controller;

import edu.spv.numbergenerateservice.service.NumberGenerateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/numbers")
@Tag(name = "Number Generation", description = "API для генерации уникальных номеров заказов")
public class NumberGenerateController {

    private final NumberGenerateService numberGenerateService;

    @Operation(summary = "Сгенерировать уникальный номер заказа",
            description = "Возвращает сгенерированный номер заказа (5 символов) и текущую дату (формат YYYYMMDD), пример 1111120241212")
    @ApiResponse(responseCode = "200", description = "Успешно сгенерирован номер заказа")
    @ApiResponse(responseCode = "500", description = "Ошибка на сервере")
    @GetMapping
    public ResponseEntity<String> getNumber() {
        String generatedUniqueNumber = numberGenerateService.generateUniqueNumber();
        return new ResponseEntity<>(generatedUniqueNumber, HttpStatus.OK);
    }
}
