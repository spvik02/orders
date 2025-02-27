package edu.spv.ordersservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Schema(hidden = true)
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
