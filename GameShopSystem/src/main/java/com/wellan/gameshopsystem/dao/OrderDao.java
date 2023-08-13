package com.wellan.gameshopsystem.dao;

import com.wellan.gameshopsystem.dto.OrderAddRequest;
import com.wellan.gameshopsystem.model.OrderInfo;
import com.wellan.gameshopsystem.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrderInfo(Integer userId, Integer total);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);

    OrderInfo getOrderInfoById(Integer orderId);

    List<OrderItem> getOrderItemById(Integer orderId);

    List<OrderInfo> getOrderInfoByUserId(Integer userId);

}
