package com.liuyuan.sell.service;

import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    /**
     * 查询一个商品
     */
    ProductInfo findOne(String s);

    /*查询所有商品（分页）*/
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 添加一个商品
     */
    ProductInfo save(ProductInfo productInfo);

    /*查询上架商品*/
    List<ProductInfo> findUpAll();

    //加库存
    void increaseStock(List<CartDTO> cartDTO);

    //减库存
    void decreaseStock(List<CartDTO> cartDTO);

    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);


}
