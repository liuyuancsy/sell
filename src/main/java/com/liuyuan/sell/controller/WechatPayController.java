package com.liuyuan.sell.controller;

import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.service.OrderMasterService;
import com.liuyuan.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class WechatPayController {
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private PayService payService;
    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId, @RequestParam("returnUrl")
                       String returnUrl, Map<String,Object> map){
        //先查询订单
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create");

    }

    /**
     * 微信回调
     */
    @RequestMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
       payService.notify(notifyData);
        return new ModelAndView("pay/success");

    }
}
