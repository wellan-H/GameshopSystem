package com.wellan.gameshopsystem.dao.impl;

import com.wellan.gameshopsystem.dao.OrderDao;
import com.wellan.gameshopsystem.dto.OrderAddRequest;
import com.wellan.gameshopsystem.model.OrderInfo;
import com.wellan.gameshopsystem.model.OrderItem;
import com.wellan.gameshopsystem.rowmapper.OrderInfoRowMapper;
import com.wellan.gameshopsystem.rowmapper.OrderItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer createOrderInfo(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO order_info(user_id,total_amount,created_date,last_modified_date) " +
                "VALUE(:userId,:totalAmount,:createdDate,:lastModifiedDate) ";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);
        Date now  = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
        String sql= "INSERT INTO order_item(order_id,game_id,plate_id,quantity,amount) " +
                "VALUES(:orderId,:gameId,:plateId,:quantity,:amount) ";
        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            mapSqlParameterSources[i]= new MapSqlParameterSource();
            mapSqlParameterSources[i].addValue("orderId",orderId);
            mapSqlParameterSources[i].addValue("gameId",orderItem.getGameId());
            mapSqlParameterSources[i].addValue("plateId",orderItem.getPlateId());
            mapSqlParameterSources[i].addValue("quantity",orderItem.getQuantity());
            mapSqlParameterSources[i].addValue("amount",orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,mapSqlParameterSources);
    }

    @Override
    public OrderInfo getOrderInfoById(Integer orderId) {
        String sql= "SELECT order_id,user_id,total_amount,created_date,last_modified_date " +
                "FROM order_info WHERE order_id=:orderId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<OrderInfo> orderInfos = namedParameterJdbcTemplate.query(sql, map, new OrderInfoRowMapper());
        if(orderInfos!=null){
            return orderInfos.get(0);
        }else return null;

    }

    @Override
    public List<OrderItem> getOrderItemById(Integer orderId) {
        String sql= "SELECT order_item_id,order_id,game_id,plate_id,quantity,amount " +
                "FROM order_item WHERE order_id=:orderId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<OrderItem> orderItems = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        return orderItems;
    }

    @Override
    public List<OrderInfo> getOrderInfoByUserId(Integer userId) {
        String sql = "SELECT order_id,user_id,total_amount,created_date,last_modified_date " +
                "FROM order_info WHERE user_id=:userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<OrderInfo> orderInfoList = namedParameterJdbcTemplate.query(sql, map, new OrderInfoRowMapper());
        return orderInfoList;
    }
}
