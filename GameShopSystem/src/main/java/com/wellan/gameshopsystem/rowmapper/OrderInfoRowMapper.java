package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.OrderInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderInfoRowMapper implements RowMapper<OrderInfo> {
    @Override
    public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(rs.getInt("order_id"));
        orderInfo.setUserId(rs.getInt("user_id"));
        orderInfo.setTotalAmount(rs.getInt("total_amount"));
        orderInfo.setCreatedDate(rs.getDate("created_date"));
        orderInfo.setLastModifiedDate(rs.getDate("last_modified_date"));
        return orderInfo;
    }
}
