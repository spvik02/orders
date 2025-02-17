package edu.spv.ordersservice.repository.SpringJdbcRepositoryImpl;

import edu.spv.ordersservice.model.Order;
import edu.spv.ordersservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderJdbcRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Order save(Order order) {
        String sql = """
                INSERT INTO orders 
                (order_number, total_amount, order_date, recipient, delivery_address, payment_type, delivery_type) 
                VALUES (?, ?, ?, ?, ?, ?, ?); 
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, order.getOrderNumber());
                    ps.setBigDecimal(2, order.getTotalAmount());
                    ps.setDate(3, Date.valueOf(order.getOrderDate()));
                    ps.setString(4, order.getRecipient());
                    ps.setString(5, order.getDeliveryAddress());
                    ps.setString(6, order.getPaymentType());
                    ps.setString(7, order.getDeliveryType());
                    return ps;
                },
                keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(id).get();
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = """
                SELECT id, order_number, total_amount, order_date, 
                recipient, delivery_address, payment_type, delivery_type 
                FROM orders 
                WHERE id = ?;
                """;
        Order order;
        try {
            order = jdbcTemplate.queryForObject(
                    sql,
                    new OrderMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            order = null;
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findByDateAndMinAmount(LocalDate date, BigDecimal minAmount) {
        String sql = """
                SELECT id, order_number, total_amount, order_date, 
                recipient, delivery_address, payment_type, delivery_type 
                FROM orders 
                WHERE order_date = ? AND total_amount > ?
                """;
        return jdbcTemplate.query(sql, new OrderMapper(), Date.valueOf(date), minAmount);
    }

    @Override
    public List<Order> findByDateRangeAndExcludeProduct(
            LocalDate startDate, LocalDate endDate, String productName) {
        String sql = """
                SELECT orders.id, order_number, total_amount, order_date, 
                recipient, delivery_address, payment_type, delivery_type 
                FROM orders 
                WHERE orders.order_date BETWEEN ? AND ?
                AND NOT EXISTS (
                SELECT 1 FROM order_details WHERE order_id = orders.id AND product_name = ?
                )
                """;
        return jdbcTemplate.query(sql, new OrderMapper(), startDate, endDate, productName);
    }

    @Override
    public void updateTotalAmount(Long id, BigDecimal newTotalAmount) {
        String updateSql = """
                UPDATE orders 
                SET total_amount = ? 
                WHERE id = ?;
                """;
        jdbcTemplate.update(updateSql, newTotalAmount, id);
    }

    private static final class OrderMapper implements RowMapper<Order> {
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setOrderNumber(rs.getString("order_number"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setOrderDate(rs.getDate("order_date").toLocalDate());
            order.setRecipient(rs.getString("recipient"));
            order.setDeliveryAddress(rs.getString("delivery_address"));
            order.setPaymentType(rs.getString("payment_type"));
            order.setDeliveryType(rs.getString("delivery_type"));
            return order;
        }
    }
}
