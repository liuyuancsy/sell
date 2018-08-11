package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {


    @Autowired
    private  OrderMasterRepository orderMasterRepository;
    private final  String OPENID="12345678900009876543";
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("天津街如家321");
        orderMaster.setBuyerName("MrLiu");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerPhone("15755149691");
        orderMaster.setOrderAmount(new BigDecimal("100"));
        orderMaster.setOrderId("11112");
        OrderMaster or =orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(or);

    }
    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest =  PageRequest.of(0,1);//第一页，1条数据
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid("12345678900009876543", pageRequest);
        Assert.assertNotEquals(null,orderMasterPage);


    }
}