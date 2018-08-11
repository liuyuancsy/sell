package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.dto.CartDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /**根据商品状态查询 **/
    public List<ProductInfo> findByProductStatus(Integer integer);


}
