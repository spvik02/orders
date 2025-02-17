package edu.spv.ordersservice.service;

import edu.spv.ordersservice.model.OrderDetails;
import edu.spv.ordersservice.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderService orderService;
    private final OrderDetailsRepository orderDetailsRepository;

    /**
     * Добавляет запись с деталями заказа, а также высчитывает и обновляет новую общую стоимость заказа с учетом созданной записи
     * @param orderDetails информация
     * @return детали заказа
     */
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        OrderDetails saved = orderDetailsRepository.save(orderDetails);

        List<OrderDetails> orderDetailsList = findByOrderId(orderDetails.getOrderId());
        BigDecimal updatedTotalAmount = orderDetailsList.stream()
                .map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderService.updateTotalAmount(orderDetails.getOrderId(), updatedTotalAmount);

        return saved;
    }

    /**
     * Возвращает список деталей заказа для указанного id заказа
     * @param orderId id заказа
     * @return список деталей заказа для указанного id заказа
     */
    public List<OrderDetails> findByOrderId(Long orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }
}
