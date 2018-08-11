package com.liuyuan.sell.service;


import com.liuyuan.sell.dataobject.SellInfo;

public interface SellInfoService {
    SellInfo findByOpenid(String openid);

}
