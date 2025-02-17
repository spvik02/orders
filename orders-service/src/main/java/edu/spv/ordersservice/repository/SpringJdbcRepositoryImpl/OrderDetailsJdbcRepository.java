package edu.spv.ordersservice.repository.SpringJdbcRepositoryImpl;

import edu.spv.ordersservice.model.OrderDetails;
import edu.spv.ordersservice.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDetailsJdbcRepository implements OrderDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public OrderDetails save(OrderDetails orderDetail) {
        String sql = "INSERT INTO order_details (product_code, product_name, quantity, unit_price, order_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, orderDetail.getProductCode());
                    ps.setString(2, orderDetail.getProductName());
                    ps.setInt(3, orderDetail.getQuantity());
                    ps.setBigDecimal(4, orderDetail.getUnitPrice());
                    ps.setLong(5, orderDetail.getOrderId());
                    return ps;
                },
                keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(id).get();
    }

    @Override
    public Optional<OrderDetails> findById(Long id) {
        String sql = """
                SELECT id, product_code, product_name, quantity, unit_price, order_id
                FROM order_details 
                WHERE id = ?;
                """;
        OrderDetails orderDetails;
        try {
            orderDetails = jdbcTemplate.queryForObject(
                    sql,
                    new OrderDetailsMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            orderDetails = null;
        }
        return Optional.ofNullable(orderDetails);
    }

    @Override
    public List<OrderDetails> findByOrderId(Long orderId) {
        String sql = "SELECT id, product_code, product_name, quantity, unit_price, order_id " +
                     "FROM order_details " +
                     "WHERE order_id = ?";
        return jdbcTemplate.query(sql, new OrderDetailsMapper(), orderId);
    }

    private static final class OrderDetailsMapper implements RowMapper<OrderDetails> {
        public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setId(rs.getLong("id"));
            orderDetails.setProductCode(rs.getInt("product_code"));
            orderDetails.setProductName(rs.getString("product_name"));
            orderDetails.setQuantity(rs.getInt("quantity"));
            orderDetails.setUnitPrice(rs.getBigDecimal("unit_price"));
            orderDetails.setOrderId(rs.getLong("order_id"));
            return orderDetails;
        }
    }
}
