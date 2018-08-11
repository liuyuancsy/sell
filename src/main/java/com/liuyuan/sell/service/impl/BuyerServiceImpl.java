package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.service.BuyerService;
import com.liuyuan.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderMasterService orderMasterService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        //查询订单
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            log.info("【查询订单】订单查询不到，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public void cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            log.info("【取消订单】订单查询不到，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        orderMasterService.cancel(orderDTO);

    }
}
