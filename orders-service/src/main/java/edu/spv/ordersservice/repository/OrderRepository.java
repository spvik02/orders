package edu.spv.ordersservice.repository;

import edu.spv.ordersservice.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByDateAndMinAmount(LocalDate date, BigDecimal minAmount);

    List<Order> findByDateRangeAndExcludeProduct(LocalDate startDate, LocalDate endDate, String productName);

    void updateTotalAmount(Long id, BigDecimal newTotalAmount);
}
