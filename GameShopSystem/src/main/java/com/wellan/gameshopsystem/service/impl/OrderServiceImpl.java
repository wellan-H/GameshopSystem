package com.wellan.gameshopsystem.service.impl;

import com.wellan.gameshopsystem.dao.GameDao;
import com.wellan.gameshopsystem.dao.OrderDao;
import com.wellan.gameshopsystem.dao.UserDao;
import com.wellan.gameshopsystem.dto.BuyItem;
import com.wellan.gameshopsystem.dto.OrderAddRequest;
import com.wellan.gameshopsystem.dto.OrderQueryParam;
import com.wellan.gameshopsystem.model.*;
import com.wellan.gameshopsystem.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional
    public Integer createOrder(Integer userId, OrderAddRequest orderAddRequest) {
        //先確認是否存在此user
        User user = userDao.getUserById(userId);
        if(user==null){
            log.warn("該userId: {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //可分為兩個表orderInfo(訂單本身)/orderItem(訂單細項)
        //由於需要取得商品的資訊才能進行計算，因此也需要引入gameDao來拿取資料
        Integer total = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : orderAddRequest.getBuyItemList()) {
            Version version = gameDao.getVersionByGameIdAndPlateId(buyItem.getGameId(),buyItem.getPlateId());
            if(version==null){
                log.warn("該商品: {} 不存在，或是對應版本: {} 不存在",gameDao.getGameById(buyItem.getGameId()).getGameName(),gameDao.getPlateNameByPlateId(buyItem.getPlateId()));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (version.getQuantity()< buyItem.getQuantity()) {
                log.warn("商品 {} 庫存不足，無法購買。\n當前庫存：{}，欲購買數量：{}",gameDao.getGameById(buyItem.getGameId()),version.getQuantity(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //扣除商品庫存
            gameDao.updateStock(buyItem.getGameId(),buyItem.getPlateId(),version.getQuantity()-buyItem.getQuantity());

            int amount = version.getPrice()*buyItem.getQuantity();
            total+=amount;
            //將buyItem轉換為orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setGameId(buyItem.getGameId());
            orderItem.setPlateId(buyItem.getPlateId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrderInfo(userId,total);
        orderDao.createOrderItem(orderId,orderItemList);
        return orderId;
    }

    @Override
    @Transactional
    public OrderDetail getOrderDetailById(Integer orderId) {
        OrderInfo orderInfo = orderDao.getOrderInfoById(orderId);
        List<OrderItem> orderItem = orderDao.getOrderItemById(orderId);
        OrderDetail orderDetail = new OrderDetail(orderInfo,orderItem);
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getOrdersByUserId(Integer userId, OrderQueryParam orderQueryParam) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<OrderInfo> orderInfoList = orderDao.getOrderInfoByUserId(userId);
        for (OrderInfo orderInfo:orderInfoList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderInfo(orderInfo);
            orderDetail.setOrderItems(orderDao.getOrderItemById(orderInfo.getOrderId()));
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
