package com.liuyuan.sell.controller;

import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderMasterService orderService;

    /**
     * 订单列表
     * @param page 第几页, 从1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch(SellException s){
            map.put("msg", s.getMessage());
            map.put("url", "/sell/seller/order/list");
            return  new ModelAndView("/common/error",map);
        }
        map.put("msg",ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @param orderDTO
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView  detail(@RequestParam("orderId") String orderId,Map<String, Object> map,OrderDTO orderDTO ) {
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException s) {
            map.put("msg", s.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("/order/detail", map);
    }
        /**
         * 完结订单
         * @param orderId
         * @param map
         * @return
         */
        @GetMapping("/finish")
        public ModelAndView finished(@RequestParam("orderId") String orderId,
                Map<String, Object> map) {
            try {
                OrderDTO orderDTO = orderService.findOne(orderId);
                orderService.finish(orderDTO);
            } catch (SellException e) {
                map.put("msg", e.getMessage());
                map.put("url", "/sell/seller/order/list");
                return new ModelAndView("common/error", map);
            }

            map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/success");
        }

}
