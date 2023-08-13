package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setGameId(rs.getInt("game_id"));
        orderItem.setPlateId(rs.getInt("plate_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));
        return orderItem;
    }
}
