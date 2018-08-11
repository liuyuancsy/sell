package com.liuyuan.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements  CodeEnum{

    CANCEL(2,"已取消"),FINISHED(1,"已完成"),NEW(0,"新订单");

    private Integer code;
    private  String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
