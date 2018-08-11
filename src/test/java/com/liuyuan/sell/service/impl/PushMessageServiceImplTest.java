package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.service.OrderMasterService;
import com.liuyuan.sell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private PushMessageService pushMessageService;

    @Test
    public void pushOrderStatus() {
        OrderDTO orderDTO =orderMasterService.findOne("1528302210366");
        pushMessageService.pushOrderStatus(orderDTO);

    }
}