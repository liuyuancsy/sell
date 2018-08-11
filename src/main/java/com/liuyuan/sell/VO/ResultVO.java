package com.liuyuan.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP请求返回的最外层对象
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 3068837394742385883L;
    private Integer code;
    private String msg;
    private  T data;
}
