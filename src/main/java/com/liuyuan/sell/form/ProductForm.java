package com.liuyuan.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
@Data
public class ProductForm {
    private String productId;

    /** 名字. */
    @NotEmpty(message = "商品名必填")
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */

    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 类目编号. */
    private Integer categoryType;
}
