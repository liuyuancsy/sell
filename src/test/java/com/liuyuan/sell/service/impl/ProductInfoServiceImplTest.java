package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@WebAppConfiguration
public class ProductInfoServiceImplTest {

    @Autowired
    private  ProductInfoServiceImpl productInfoService;
    @Test
    public void findOne() {
        ProductInfo productInfo =productInfoService.findOne("111232");
        Assert.assertNotNull(productInfo);

    }
    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        Assert.assertNotNull(productInfos);
    }
    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(4);
        productInfo.setProductDescription("很好吃的饭");
        productInfo.setProductIcon("xxxxx.jpg");
        productInfo.setProductId("11321");
        productInfo.setProductName("台湾卤肉饭");
        productInfo.setProductPrice(new BigDecimal(19));
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductStock(1);
        productInfoService.save(productInfo);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList =productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void onsale(){
        ProductInfo productInfo =productInfoService.onSale("11321");
        Assert.assertTrue(0==productInfo.getProductStatus());

    }
    @Test
    public void offsale(){
        ProductInfo productInfo =productInfoService.offSale("11321");
        Assert.assertTrue(1==productInfo.getProductStatus());

    }
}