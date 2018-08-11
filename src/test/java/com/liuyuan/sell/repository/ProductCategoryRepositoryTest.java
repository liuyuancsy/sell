package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.ProductCategory;
import org.hibernate.mapping.Array;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private  ProductCategoryRepository productCategoryRepository;
    Logger logger =LoggerFactory.getLogger(ProductCategoryRepositoryTest.class);
    @Test
    @Transactional
    public void add(){//添加
        ProductCategory productCategory = new ProductCategory("顾客最喜爱",6);

        productCategoryRepository.save(productCategory);

    }
    @Test
    public void query(){
       productCategoryRepository.findById(1);

    }
    @Test
    public  void  findByCatagoryType(){
        List<Integer> lis=Arrays.asList(1,2,3);
        List<ProductCategory> object = productCategoryRepository.findByCategoryTypeIn(lis);
        Assert.assertNotEquals(null,object);
        logger.info(object.toString());
    }

}