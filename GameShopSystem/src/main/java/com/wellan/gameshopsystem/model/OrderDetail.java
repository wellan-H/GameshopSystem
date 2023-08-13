package com.wellan.gameshopsystem.model;

import lombok.Data;

import java.util.List;
@Data
public class OrderDetail {
    public OrderDetail() {
    }

    public OrderDetail(OrderInfo orderInfo, List<OrderItem> orderItems) {
        this.orderInfo = orderInfo;
        this.orderItems = orderItems;
    }

    private OrderInfo orderInfo;
    private List<OrderItem> orderItems;
}
