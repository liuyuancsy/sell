package com.liuyuan.sell.dto;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
@Data
public class CartDTO {
    private String productId;
    private  Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
