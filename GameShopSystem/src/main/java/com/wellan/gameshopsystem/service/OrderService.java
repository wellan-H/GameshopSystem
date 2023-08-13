package com.wellan.gameshopsystem.service;

import com.wellan.gameshopsystem.dto.OrderAddRequest;
import com.wellan.gameshopsystem.dto.OrderQueryParam;
import com.wellan.gameshopsystem.model.OrderDetail;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, OrderAddRequest orderAddRequest);

    OrderDetail getOrderDetailById(Integer orderId);

    List<OrderDetail> getOrdersByUserId(Integer userId, OrderQueryParam orderQueryParam);
}
