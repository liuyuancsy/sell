package com.liuyuan.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConf {
    private  String appId;
    private  String secret;
    private String mchId;
    private String mchKey;
    /*商户证书根路径*/
    private String keyPath;
    /*回调url*/
    private  String notifyUrl;

    private  String openAppId;
    private String openAppSecret;
}
