package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.ProductCategory;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.repository.ProductCategoryRepository;
import com.liuyuan.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CatagoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository repo;
    @Override
    public ProductCategory findOne(Integer id) {
        return repo.findById(id).orElse(null);//2.1版本以后写法;
    }

    @Override
    public List<ProductCategory> findByCategoryIn(List<Integer> list) {
        return repo.findByCategoryTypeIn(list);
    }

    @Override
    public Page<ProductCategory> findAll(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = repo.findAll(pageable);
        if (productCategoryPage == null) {
            throw new SellException(ResultEnum.CATEGORY_NULL);
        }
        return productCategoryPage;
    }
    @Override
    public List<ProductCategory> findAll(){
        return repo.findAll();
    }
}
