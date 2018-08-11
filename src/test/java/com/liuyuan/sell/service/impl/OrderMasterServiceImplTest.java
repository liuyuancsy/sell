package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.OrderDetail;
import com.liuyuan.sell.dto.CartDTO;
import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.enums.OrderPayStatusEnum;
import com.liuyuan.sell.enums.OrderStatusEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest

public class OrderMasterServiceImplTest {

    @Autowired
    private  OrderMasterServiceImpl orderMasterService;
    Logger logger = LoggerFactory.getLogger(OrderMasterServiceImplTest.class);


    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("如家酒店");
        orderDTO.setBuyerName("刘源");
        orderDTO.setBuyerOpenid("1234567890");
        orderDTO.setBuyerPhone("15755149691");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("111232");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("11321");
        orderDetail1.setProductQuantity(1);
        orderDetailList.add(orderDetail1);
        orderDTO.setOrderDetailList(orderDetailList);
        orderMasterService.create(orderDTO);

    }

    @Test
    public void findOne() {
        OrderDTO orderDTO =orderMasterService.findOne("1528302210366");
        logger.info(orderDTO.toString());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderDTO> orderDTOPage =orderMasterService.findList("1234567890",pageRequest);
        logger.info(orderDTOPage.getContent().toString());
    }

    @Test
    public void finsh() {
        OrderDTO orderDTO = orderMasterService.findOne("1528302210366");
        OrderDTO result=orderMasterService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());

    }

    @Test
    public void cancel() {
        OrderDTO orderDTO =orderMasterService.findOne("1528302210366");
        OrderDTO orderDTO1 =orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderDTO1.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderMasterService.findOne("1528302210366");
        OrderDTO result=orderMasterService.paid(orderDTO);
        Assert.assertEquals(OrderPayStatusEnum.SUCCESS.getCode(),result.getPayStatus());

    }

    @Test
    public void list(){
        PageRequest pageRequest =PageRequest.of(0,4);
        Page<OrderDTO> orderDTOPage =orderMasterService.findList(pageRequest);
        Assert.assertTrue("查询所有订单",orderDTOPage.getTotalPages()>0);

    }
}