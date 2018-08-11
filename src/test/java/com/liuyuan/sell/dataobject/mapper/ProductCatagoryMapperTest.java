//package com.liuyuan.sell.dataobject.mapper;
//
//import com.liuyuan.sell.dataobject.ProductCategory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ProductCatagoryMapperTest {
//    @Autowired
//    private ProductCatagoryMapper productCatagoryMapper;
//
//    @Test
//    public void insertByObject() {
//        ProductCategory productCatagory = new ProductCategory();
//        productCatagory.setCategoryName("师妹最爱");
//        productCatagory.setCategoryType(112);
//        int i=productCatagoryMapper.insertByObject(productCatagory);
//        Assert.assertEquals(1,i);
//
//    }
//}