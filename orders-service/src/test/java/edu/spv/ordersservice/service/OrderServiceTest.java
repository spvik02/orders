package edu.spv.ordersservice.service;

import edu.spv.ordersservice.model.Order;
import edu.spv.ordersservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderNumberService orderNumberService;
    @Captor
    private ArgumentCaptor<Order> argumentCaptor;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(1L, "1234520250214", BigDecimal.valueOf(0.0),
                LocalDate.of(2025, 2, 14), "Zagreus",
                "Underworld", "credit card", "delivery");
    }

    @Test
    void checkCreateOrderShouldCallRepositoryMethod() {
        orderService.createOrder(order);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void checkCreateOrderShouldSetOrderNumber() {
        when(orderNumberService.getOrderNumber()).thenReturn("1234520250214");

        orderService.createOrder(order);

        verify(orderRepository).save(argumentCaptor.capture());
        Order captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getOrderNumber()).isEqualTo("1234520250214");
    }

    @Test
    void checkCreateOrderShouldSetCurrentDate() {
        orderService.createOrder(order);

        verify(orderRepository).save(argumentCaptor.capture());
        Order captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getOrderDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void checkCreateOrderShouldSetTotalAmountToZero() {
        orderService.createOrder(order);

        verify(orderRepository).save(argumentCaptor.capture());
        Order captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getTotalAmount()).isEqualTo(BigDecimal.valueOf(0.0));
    }

    @Test
    void checkGetOrderByIdShouldCallRepositoryMethod() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.getOrderById(1L);

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void checkGetOrderByIdShouldThrowNoSuchElementException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            orderService.getOrderById(1L);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void checkFindOrdersByDateAndMinAmountShouldCallRepositoryMethod() {
        orderService.findOrdersByDateAndMinAmount(LocalDate.of(2025, 2, 14),
                BigDecimal.valueOf(0.0));

        verify(orderRepository, times(1))
                .findByDateAndMinAmount(LocalDate.of(2025, 2, 14),
                        BigDecimal.valueOf(0.0));
    }

    @Test
    void checkFindOrdersByDateRangeAndExcludeProductShouldCallRepositoryMethod() {
        orderService.findByDateRangeAndExcludeProduct(LocalDate.of(2024, 2, 14),
                LocalDate.of(2025, 2, 15), "Laptop");

        verify(orderRepository, times(1))
                .findByDateRangeAndExcludeProduct(LocalDate.of(2024, 2, 14),
                        LocalDate.of(2025, 2, 15), "Laptop");
    }

    @Test
    void checkUpdateTotalAmountShouldCallRepositoryMethod() {
        orderService.updateTotalAmount(1L, BigDecimal.ONE);

        verify(orderRepository, times(1)).updateTotalAmount(1L, BigDecimal.ONE);
    }
}
