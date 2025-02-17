package edu.spv.ordersservice.controller;

import edu.spv.ordersservice.model.Order;
import edu.spv.ordersservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "API для управления заказами")
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates an order.
     *
     * @param order data for new order
     * @return creates order
     */
    @Operation(summary = "Создание заказа",
            description = "Создает новый заказ и возвращает созданный заказ.")
    @ApiResponse(responseCode = "201",
            description = "Заказ успешно создан",
            content = @Content(schema = @Schema(implementation = Order.class)))
    @ApiResponse(responseCode = "400",
            description = "Неверные данные для создания заказа")
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody @Parameter(description = "Данные нового заказа") Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Returns an order by its ID
     *
     * @param id order id
     * @return order with passed id
     */
    @Operation(summary = "Получение заказа по идентификатору", description = "Возвращает заказ по его ID.")
    @ApiResponse(responseCode = "200", description = "Заказ найден", content = @Content(schema = @Schema(implementation = Order.class)))
    @ApiResponse(responseCode = "404", description = "Заказ с указанным ID не найден")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        try {
            Order orderById = orderService.getOrderById(id);
            return ResponseEntity.ok(orderById);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    /**
     * Returns the list of orders for the specified date and more than the specified total amount
     *
     * @param date
     * @param minAmount
     * @return
     */
    @Operation(summary = "Получение списка заказов за заданную дату и больше заданной общей суммы заказа",
            description = "Возвращает заказы, сделанные в указанную дату и с общей суммой больше заданного значения.")
    @ApiResponse(responseCode = "200", description = "Список заказов найден",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @ApiResponse(responseCode = "400", description = "Неверные параметры фильтрации")
    @GetMapping("/filter/by-date-and-min-amount")
    public ResponseEntity<List<Order>> getOrdersByDateAndAmount(
            @RequestParam @Parameter(description = "Дата заказа") LocalDate date,
            @RequestParam @Parameter(description = "Минимальная сумма заказа") BigDecimal minAmount) {
        return ResponseEntity.ok(orderService.findOrdersByDateAndMinAmount(date, minAmount));
    }

    /**
     * Returns the list of orders that do not contain the specified product and were received during the specified
     * time period.
     *
     * @param productName
     * @param startDate
     * @param endDate
     * @return
     */
    @Operation(summary = "Получение списка заказов, не содержащих заданный товар и поступивших в заданный временной период.",
            description = "Возвращает заказы, которые не содержат заданный товар и поступили в указанный временной период.")
    @ApiResponse(responseCode = "200", description = "Список заказов найден",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @ApiResponse(responseCode = "400", description = "Неверные параметры фильтрации")
    @GetMapping("/filter/exclude-product-and-in-time-period")
    public ResponseEntity<List<Order>> getOrdersWithoutProductAndInTimePeriod(
            @RequestParam @Parameter(description = "Название товара") String productName,
            @RequestParam @Parameter(description = "Начальная дата периода") LocalDate startDate,
            @RequestParam @Parameter(description = "Конечная дата периода") LocalDate endDate) {
        return ResponseEntity.ok(orderService.findByDateRangeAndExcludeProduct(startDate, endDate, productName));
    }
}
