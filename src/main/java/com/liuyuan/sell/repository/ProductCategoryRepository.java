package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
    public Page<ProductCategory> findAll(Pageable pageable);
}
