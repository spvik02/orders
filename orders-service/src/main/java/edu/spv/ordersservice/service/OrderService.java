package edu.spv.ordersservice.service;

import edu.spv.ordersservice.model.Order;
import edu.spv.ordersservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderNumberService orderNumberService;

    public Order createOrder(Order order) {
        order.setOrderNumber(orderNumberService.getOrderNumber());
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(BigDecimal.valueOf(0.0));

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order with id " + id + " wasn't found"));
    }

    /**
     * Получение заказов за заданную дату и больше заданной общей суммы заказа
     * @param date дата
     * @param minAmount минимальная сумма заказа
     * @return список заказов соответствущих требованиям
     */
    public List<Order> findOrdersByDateAndMinAmount(LocalDate date, BigDecimal minAmount) {
        return orderRepository.findByDateAndMinAmount(date, minAmount);
    }

    /**
     * Получение списка заказов, не содержащих заданный товар и поступивших в заданный временной период.
     * @param startDate начало периода
     * @param endDate конец периода
     * @param productName название продукта, который должен быть исключен
     * @return список заказов соответствущих требованиям
     */
    public List<Order> findByDateRangeAndExcludeProduct(LocalDate startDate, LocalDate endDate, String productName) {
        return orderRepository.findByDateRangeAndExcludeProduct(startDate, endDate, productName);
    }

    /**
     * Обновляет общую стоимость заказа
     * @param id номер заказа
     * @param newTotalAmount новая общая стоимость заказа
     */
    public void updateTotalAmount(Long id, BigDecimal newTotalAmount) {
        orderRepository.updateTotalAmount(id, newTotalAmount);
    }
}
