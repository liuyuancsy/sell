package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dataobject.SellInfo;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.repository.SellInfoRepository;
import com.liuyuan.sell.service.SellInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SellInfoServiceImpl implements SellInfoService {
    @Autowired
    private SellInfoRepository sellInfoRepository;


    @Override
    public SellInfo findByOpenid(String openid) {
        SellInfo sellInfo = sellInfoRepository.findByOpenid(openid);
        if (sellInfo == null) {
            throw new SellException(ResultEnum.QUERY_SELLERINFO_FAIL);
        }
        return sellInfo;
    }
}
