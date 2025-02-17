package edu.spv.ordersservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private Long id;
    @NotNull
    private Integer productCode;
    @NotNull
    private String productName;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal unitPrice;
    @NotNull
    private Long orderId;
}
