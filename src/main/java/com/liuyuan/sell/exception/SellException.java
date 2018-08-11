package com.liuyuan.sell.exception;

import lombok.Data;


import com.liuyuan.sell.enums.ResultEnum;
@Data
public class SellException extends  RuntimeException {
    private  Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
