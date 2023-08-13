package com.wellan.gameshopsystem.model;

import lombok.Data;

import java.util.Date;
@Data
public class OrderInfo {
    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createdDate;
    private Date lastModifiedDate;
}
