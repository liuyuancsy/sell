package com.liuyuan.sell.enums;

import lombok.Getter;

@Getter
public enum OrderPayStatusEnum implements CodeEnum{
    SUCCESS(0,"已支付"),WAIT(1,"待支付");
    private  Integer code;
    private  String msg;

    OrderPayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
