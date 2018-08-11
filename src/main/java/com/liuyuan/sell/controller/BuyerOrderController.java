package com.liuyuan.sell.controller;

import com.liuyuan.sell.service.impl.BuyerServiceImpl;
import com.liuyuan.sell.utils.ResultVoUtil;
import com.liuyuan.sell.VO.ResultVO;
import com.liuyuan.sell.converter.OrderForm2OrderDTOConverter;
import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.form.OrderForm;
import com.liuyuan.sell.service.impl.OrderMasterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;
    @Autowired
    private BuyerServiceImpl buyerService;
    /**
     * 创建订单
     * @return ResultVO
     */
    @RequestMapping(value = "/create")
    public ResultVO createOrder(@Valid OrderForm orderFrom, BindingResult bindingResult){
        //
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】,参数不正确,orderForm={},",orderFrom);
            throw  new SellException(bindingResult.getFieldError().getDefaultMessage(),ResultEnum.PARAM_ERROR.getCode());
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO = OrderForm2OrderDTOConverter.convert(orderFrom);
        OrderDTO result =orderMasterService.create(orderDTO);
        String orderId = result.getOrderId();
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        return  ResultVoUtil.success(map);
    }

    /**
     * 查询订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     */

    @GetMapping("/list")
    public ResultVO orderList(@RequestParam(value = "openid") String openid,@RequestParam(value = "page" ,defaultValue = "0") Integer
                              page,@RequestParam(value="size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】,openid不能为空");
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(openid,pageRequest);
        ResultVO resultList = ResultVoUtil.success(orderDTOPage.getContent());//不能直接返回传orderDTOpage
        return  resultList;
    }

    //订单详情
    @GetMapping(value = "/detail")
    public ResultVO<OrderDTO> findDetail(@RequestParam(value = "openid") String openid, @RequestParam(value = "orderId") String orderId) {
        //判断该订单是否属于当前用户
        OrderDTO orderDTO =buyerService.findOrderOne(openid,orderId);
        return ResultVoUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping(value = "cancel")
    public ResultVO cancel(@RequestParam(value = "openid") String openid, @RequestParam(value = "orderId") String orderID){
        buyerService.cancelOrder(openid,orderID);
        return ResultVoUtil.success();
    }

}

