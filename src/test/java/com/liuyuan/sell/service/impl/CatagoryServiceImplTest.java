package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CatagoryServiceImplTest {

    @Autowired
    CatagoryServiceImpl catagoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory =catagoryService.findOne(3);
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void findByCategoryIn() {
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> productCategoryList = catagoryService.findByCategoryIn(list);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(1,1);
        Page<ProductCategory> productCategoryList = catagoryService.findAll(pageRequest);
        Assert.assertNotNull(productCategoryList.getTotalPages());
    }

}