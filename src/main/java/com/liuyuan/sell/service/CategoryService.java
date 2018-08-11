package com.liuyuan.sell.service;

import com.liuyuan.sell.dataobject.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    public ProductCategory findOne(Integer id);
    public List<ProductCategory> findByCategoryIn(List<Integer> list);
    public Page<ProductCategory> findAll(Pageable pageable);
    public List<ProductCategory> findAll();


}
