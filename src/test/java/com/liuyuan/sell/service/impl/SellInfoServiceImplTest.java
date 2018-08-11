package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.SellInfo;
import com.liuyuan.sell.service.SellInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellInfoServiceImplTest {
    @Autowired
    private SellInfoService sellInfoService;

    @Test
    public void findByOpenid() {
        SellInfo sellInfo=sellInfoService.findByOpenid("ab7c");
        Assert.assertNotNull(sellInfo);
    }
}