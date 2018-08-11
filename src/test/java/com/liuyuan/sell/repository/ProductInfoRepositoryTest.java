package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Test
    public void findByProductStatus() {
        List<ProductInfo> po = productInfoRepository.findByProductStatus(0);
        Assert.assertNotNull(po);
    }
    @Test
    public void  saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(3);
        productInfo.setProductDescription("很好吃的粥");
        productInfo.setProductIcon("xxxxx.jpg");
        productInfo.setProductId("111232");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(9));
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductStock(2);
        productInfoRepository.save(productInfo);
//        Assert.assert

    }

}