package com.liuyuan.sell.service;

import com.liuyuan.sell.dataobject.OrderMaster;
import com.liuyuan.sell.dto.CartDTO;
import com.liuyuan.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderMasterService {
    /*创建订单*/
    OrderDTO create(OrderDTO orderDTO);
    /*查询单个订单*/
   OrderDTO findOne(String orderId);
    /*查询订单列表*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    /*完结订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /*取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /*支付订单*/
    OrderDTO paid(OrderDTO orderDTO);
    /*买家查询订单列表*/

    Page<OrderDTO> findList( Pageable pageable);


}
