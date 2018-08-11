package com.liuyuan.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatPayConfig {
    @Autowired
    private WechatAccountConf wechatAccountConf;

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }
    @Bean
    private WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConf.getAppId());
        wxPayH5Config.setAppSecret(wechatAccountConf.getSecret());
        wxPayH5Config.setKeyPath(wechatAccountConf.getKeyPath());
        wxPayH5Config.setMchId(wechatAccountConf.getMchId());
        wxPayH5Config.setMchKey(wechatAccountConf.getMchKey());
        wxPayH5Config.setNotifyUrl(wechatAccountConf.getNotifyUrl());
        return  wxPayH5Config;
    }
}
