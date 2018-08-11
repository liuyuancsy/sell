package com.liuyuan.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WeChatOpenConfig {
    @Autowired
    private WechatAccountConf wechatAccountConf;
    @Bean
    public WxMpService wxMpService (){
        WxMpService wxOpenService =new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }
    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wechatAccountConf.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(wechatAccountConf.getOpenAppSecret());
        return wxMpInMemoryConfigStorage;
    }
}
