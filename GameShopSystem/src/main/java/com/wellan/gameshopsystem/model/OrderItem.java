package com.wellan.gameshopsystem.model;

import lombok.Data;

@Data
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer gameId;
    private Integer plateId;
    private Integer quantity;
    private Integer amount;
}
