package com.liuyuan.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"取消成功"),
    PARAM_ERROR(1,"参数有误"),
    PROCUCT_NOT_EXIST(10,"商品不存在"),PRODUCT_OUT_OF(11,"商品数量不足")
    ,ORDER_NOT_EXIST(12,"订单不存在"),ORDER_DETAIL_NOT_EXIST(13,"订单明细不存在")
    ,ORDER_STATUS_ERROR(14,"订单状态不正确"),ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_PAY_ERROR(16,"订单已支付"),
    ORDER_CREATE_FAIL(17,"订单创建失败"),ORDER_OWNER_ERROR(18,"订单不属于当前用户"),
    WECHAT_MP_ERROR(19,"微信公众号错误"),WXPAY_NOTIFY_MONEY_VERIFY_ERROR(20,"微信回调金额不一致"),
    CANCEL_FAIL(21,"取消订单失败"),
    ORDER_FINISH_SUCCESS(22,"完结订单成功"),PRODUCT_STATUS_ERROR(23,"商品状态有误"),
    PRODUCT_UPDATE_FAIL(23,"添加/修改商品失败"),
    CATEGORY_NULL(24,"查询不到类目信息"),CATEGORY_SUCCESS(25,"添加/修改类目成功"),
    WECHAT_OPEN_ERROR(26,"微信开发平台错误"),QUERY_SELLERINFO_FAIL(27,"查询卖家信息失败"),
    LOGOUT_SUCCESS(28,"登出成功");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
