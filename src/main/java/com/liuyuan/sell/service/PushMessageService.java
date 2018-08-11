package com.liuyuan.sell.service;

import com.liuyuan.sell.dto.OrderDTO;

public interface PushMessageService {
    void pushOrderStatus(OrderDTO orderDTO);
}
