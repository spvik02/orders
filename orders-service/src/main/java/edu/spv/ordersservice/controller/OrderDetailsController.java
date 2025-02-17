package edu.spv.ordersservice.controller;

import edu.spv.ordersservice.model.OrderDetails;
import edu.spv.ordersservice.service.OrderDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/details")
@RequiredArgsConstructor
@Tag(name = "Order Details", description = "API для управления деталями заказа")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    /**
     * Adds Details to specified order
     * @param orderDetails Детали заказа для добавления
     * @return Созданные детали заказа
     */
    @Operation(summary = "Добавить детали к заказу", description = "Добавляет детали к указанному заказу.")
    @ApiResponse(responseCode = "201", description = "Детали заказа успешно добавлены",
            content = @Content(schema = @Schema(implementation = OrderDetails.class)))
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @PostMapping
    public ResponseEntity<OrderDetails> addOrderDetails(
            @RequestBody @Parameter(description = "Детали заказа") OrderDetails orderDetails) {
        OrderDetails createdOrderDetails = orderDetailsService.createOrderDetails(orderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDetails);
    }
}
