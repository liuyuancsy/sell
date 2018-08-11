package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    WxMpService wxMpService;

    @Override
    public void pushOrderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("n5IDpsIfcJHpUkdvpr-ENlqmYaCK1V-K7IxJe1P1NeM");
        templateMessage.setToUser("osdlD0Qo3pO3FpaVnrAN0pvBDExI");
        List<WxMpTemplateData> listData = Arrays.asList(
                new WxMpTemplateData("first","这是测试推送的消息")
        );

        templateMessage.setData(listData);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【推送消息】推送失败,{}",e);
        }

    }
}
