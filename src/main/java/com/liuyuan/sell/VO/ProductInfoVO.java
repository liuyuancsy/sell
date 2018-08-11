package com.liuyuan.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liuyuan.sell.dataobject.ProductInfo;
import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 */
@Data
public class ProductInfoVO
{
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;
     ProductInfoVO(ProductInfo productInfo){
        productId=productInfo.getProductId();
        productName=productInfo.getProductName();
        productPrice=productInfo.getProductPrice();
        productDescription=productInfo.getProductDescription();
        productIcon=productInfo.getProductIcon();

    }
public  ProductInfoVO(){

}
    @Test
    public void test(){
        List<ProductInfo> productInfoList = new ArrayList<>();
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("test1");
        productInfo.setProductDescription("testd");
        productInfoList.add(productInfo);
        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setProductId("2");
        productInfo1.setProductIcon("/dfdaf");
        productInfoList.add(productInfo1);
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        productInfoList.stream().forEach(list->productInfoVOList.add(new ProductInfoVO(list)));
        productInfoVOList.stream().forEach(list->System.out.println(list));
    }
    @Override
    public String toString() {
        return "ProductInfoVO{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                '}';
    }
}
