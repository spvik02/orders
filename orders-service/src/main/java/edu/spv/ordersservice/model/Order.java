package edu.spv.ordersservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(hidden = true)
    private Long id;
    @NotNull
    @Schema(hidden = true)
    private String orderNumber;
    @NotNull
    @Schema(hidden = true)
    private BigDecimal totalAmount;
    @NotNull
    @Schema(hidden = true)
    private LocalDate orderDate;
    @NotNull
    private String recipient;
    private String deliveryAddress;
    @NotNull
    private String paymentType;
    @NotNull
    private String deliveryType;
}
