package edu.spv.ordersservice.repository;

import edu.spv.ordersservice.model.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepository {

    OrderDetails save(OrderDetails orderDetail);

    Optional<OrderDetails> findById(Long id);

    List<OrderDetails> findByOrderId(Long orderId);
}
