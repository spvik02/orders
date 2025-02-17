package edu.spv.ordersservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    @NotNull
    private String orderNumber;
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private String recipient;
    private String deliveryAddress;
    @NotNull
    private String paymentType;
    @NotNull
    private String deliveryType;
}
