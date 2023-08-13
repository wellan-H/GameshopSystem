package com.wellan.gameshopsystem.controller;

import com.wellan.gameshopsystem.dto.OrderAddRequest;
import com.wellan.gameshopsystem.dto.OrderQueryParam;
import com.wellan.gameshopsystem.model.OrderDetail;
import com.wellan.gameshopsystem.service.OrderService;
import com.wellan.gameshopsystem.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    //創建訂單
    @PostMapping("/users/{userId}/createOrder")
    public ResponseEntity<OrderDetail> createOrder(@PathVariable Integer userId,
                                                   @RequestBody @Valid OrderAddRequest orderAddRequest){
        Integer orderId = orderService.createOrder(userId,orderAddRequest);
        OrderDetail orderDetail = orderService.getOrderDetailById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetail);
    }
    //查詢訂單
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<OrderDetail>> getOrders(@PathVariable Integer userId,
                                                       @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                       @RequestParam(defaultValue = "0") @Min(0) Integer offset ){
        OrderQueryParam orderQueryParam = new OrderQueryParam();
        orderQueryParam.setLimit(limit);
        orderQueryParam.setOffset(offset);
        List<OrderDetail> orderDetails = orderService.getOrdersByUserId(userId,orderQueryParam);
        Page<OrderDetail> orders = new Page<>();
        orders.setList(orderDetails);
        orders.setTotal(orderDetails.size());
        orders.setLimit(limit);
        orders.setOffset(offset);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
