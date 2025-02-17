package edu.spv.ordersservice.service;

import edu.spv.ordersservice.model.OrderDetails;
import edu.spv.ordersservice.repository.OrderDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDetailsServiceTest {

    @Mock
    private OrderService orderService;
    @Mock
    private OrderDetailsRepository orderDetailsRepository;
    @Captor
    private ArgumentCaptor<BigDecimal> argumentCaptor;

    @InjectMocks
    private OrderDetailsService orderDetailsService;

    @Test
    void checkCreateOrderDetailsShouldCallSaveRepositoryMethod() {
        OrderDetails orderDetails = new OrderDetails(1L, 1, "Laptop", 1,
                BigDecimal.TEN, 1L);

        orderDetailsService.createOrderDetails(orderDetails);

        verify(orderDetailsRepository, times(1)).save(orderDetails);
    }

    @Test
    void checkCreateOrderDetailsShouldCallUpdateTotalAmount() {
        OrderDetails orderDetails = new OrderDetails(1L, 1, "Laptop", 1,
                BigDecimal.TEN, 1L);
        when(orderDetailsRepository.findByOrderId(anyLong()))
                .thenReturn(List.of(orderDetails));

        orderDetailsService.createOrderDetails(orderDetails);

        verify(orderService, times(1)).updateTotalAmount(1L, BigDecimal.TEN);
    }

    @Test
    void checkCreateOrderDetailsShouldCalculateNewTotalAmount() {
        OrderDetails orderDetails = new OrderDetails(1L, 1, "Laptop", 1,
                BigDecimal.TEN, 1L);
        when(orderDetailsRepository.findByOrderId(anyLong()))
                .thenReturn(List.of(orderDetails, orderDetails, orderDetails));

        orderDetailsService.createOrderDetails(orderDetails);

        verify(orderService).updateTotalAmount(anyLong(), argumentCaptor.capture());
        BigDecimal newTotalAmountArgumentCaptor = argumentCaptor.getValue();

        assertThat(newTotalAmountArgumentCaptor).isEqualTo(BigDecimal.valueOf(30));
    }

    @Test
    void checkFindByOrderIdShouldCallRepositoryMethod() {
        orderDetailsService.findByOrderId(1L);

        verify(orderDetailsRepository, times(1)).findByOrderId(1L);
    }
}
