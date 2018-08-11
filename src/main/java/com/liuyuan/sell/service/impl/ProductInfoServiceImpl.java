package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.dto.CartDTO;
import com.liuyuan.sell.enums.ProductStatusEnum;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.repository.ProductInfoRepository;
import com.liuyuan.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findById(productId).orElse(null);//springboot 版本问题
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);

    }

    @Override
    public List<ProductInfo> findUpAll() {
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoList;
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTO) {
        for (CartDTO cartDTO1 :cartDTO) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO1.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PROCUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO1.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    /**
     * 减库存（事务）
     * @param cartDTO
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTO) {
        for (CartDTO cartDTO1 :cartDTO){
            ProductInfo productInfo=productInfoRepository.findById(cartDTO1.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PROCUCT_NOT_EXIST);
            }
            Integer curel  =productInfo.getProductStock()-cartDTO1.getProductQuantity();
            if (curel < 0) {
                throw  new SellException(ResultEnum.PRODUCT_OUT_OF);
            }else{//否则减掉库存
                productInfo.setProductStock(curel);
                productInfoRepository.save(productInfo);
            }
        }

    }

    //上架
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null)
            throw  new SellException(ResultEnum.PROCUCT_NOT_EXIST);
        if(productInfo.getProductStatus()==ProductStatusEnum.UP.getCode())
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo=productInfoRepository.save(productInfo);
        return productInfo;
    }

    //下架
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null)
            throw  new SellException(ResultEnum.PROCUCT_NOT_EXIST);
        if(productInfo.getProductStatus()==ProductStatusEnum.DOWN.getCode())
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo=productInfoRepository.save(productInfo);
        return productInfo;
    }


}
