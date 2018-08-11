package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Test
    public  void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("2222222");
        orderDetail.setOrderId("111111");
        orderDetail.setProductIcon("http://xxxxx.jpg");
        orderDetail.setProductId("111112");
        orderDetail.setProductName("皮蛋瘦肉粥");
        orderDetail.setProductPrice(new BigDecimal("12.00"));
        orderDetail.setProductQuantity(2);
        OrderDetail orderDetail1 =orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(orderDetail);
    }
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList =orderDetailRepository.findByOrderId("111111");
        Assert.assertEquals(1,orderDetailList.size());
    }
}