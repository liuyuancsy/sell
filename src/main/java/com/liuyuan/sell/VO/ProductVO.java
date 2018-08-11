package com.liuyuan.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liuyuan.sell.dataobject.ProductInfo;
import lombok.Data;

import java.util.List;

/**
 * 商品包含类目
 */
@Data
public class ProductVO {

    @JsonProperty("name")//数据库字段和查询返回字段的映射关系
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
