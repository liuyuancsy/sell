package com.liuyuan.sell.service;

import com.liuyuan.sell.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;

public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderID);
    //取消订单
    void cancelOrder(String openid ,String orderId);
}
