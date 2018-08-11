package com.liuyuan.sell.service.impl;

import com.liuyuan.sell.service.PayService;
import com.liuyuan.sell.service.WebSocket;
import com.liuyuan.sell.utils.KeyUtil;
import com.liuyuan.sell.converter.OrderMaster2OrderDTO;
import com.liuyuan.sell.dataobject.OrderDetail;
import com.liuyuan.sell.dataobject.OrderMaster;
import com.liuyuan.sell.dataobject.ProductInfo;
import com.liuyuan.sell.dto.CartDTO;
import com.liuyuan.sell.dto.OrderDTO;
import com.liuyuan.sell.enums.OrderPayStatusEnum;
import com.liuyuan.sell.enums.OrderStatusEnum;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.repository.OrderDetailRepository;
import com.liuyuan.sell.repository.OrderMasterRepository;
import com.liuyuan.sell.repository.ProductInfoRepository;
import com.liuyuan.sell.service.OrderMasterService;
import com.liuyuan.sell.service.ProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    protected ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private PayService payService;
    @Autowired
    private WebSocket webSocket;
    Logger logger = LoggerFactory.getLogger(OrderMasterServiceImpl.class);
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        /**创建订单的逻辑:查询商品的数量，计算总金额，写入订单数据库（orderMaster和orderDetail）,下单成功扣除库存
        (单价要查数据库)**/
        //1查询商品，价格、数量
        BigDecimal totalAmt = new BigDecimal(BigInteger.ZERO);
        String orderId =KeyUtil.keyGenerate();
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
          ProductInfo productInfo=productInfoRepository.findById(orderDetail.getProductId()).orElse(null);
          if(productInfo==null)
              throw new SellException(ResultEnum.PROCUCT_NOT_EXIST);
            //2、计算总金额
            totalAmt = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(totalAmt);
            //3、订单详情入库
            orderDetail.setDetailId(KeyUtil.keyGenerate());
            orderDetail.setOrderId(orderId);//为什么??
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //4写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(totalAmt);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(OrderPayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //5扣库存
        List<CartDTO> cartDTOSList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOSList);//重难点

        //发送websocket消息
        webSocket.sendMessage(orderMaster.getOrderId());//参数不能为空!
        return orderDTO;
    }
    @Override
    @Transactional
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster =orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList =orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size() == 0) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDetailList(orderDetailList);
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage =orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderMaster> orderMasterList =orderMasterPage.getContent();
        List<OrderDTO> orderDTOList =OrderMaster2OrderDTO.convert(orderMasterList);
       return new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        //前端不显示详情，为什么？
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //先判断订单状态，若不是新下单，不允许完成订单
        if (OrderStatusEnum.NEW.getCode() != orderDTO.getOrderStatus()) {
            logger.error("【完成订单】订单状态有误,orderDTO={}", orderDTO);
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            logger.error("【完成订单】更新状态失败,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);

        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //查询订单状态
        OrderMaster orderMaster = new OrderMaster();
        if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
            logger.info("【取消订单】订单状态有误,orderId={},orderStatus={}", orderMaster.getOrderId() + orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            logger.error("【取消订单】更新状态失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        //先判断有没有库存
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        if(CollectionUtils.isEmpty(orderDetailList)){
            logger.error("【取消订单】,订单中无商品详情，orderDTO={}",orderDTO);
            throw  new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //若已支付,则退款
        if (orderDTO.getOrderStatus() == OrderStatusEnum.FINISHED.getCode()) {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (OrderStatusEnum.NEW.getCode() != orderDTO.getOrderStatus()) {
            logger.error("【订单支付完成】订单状态有误,orderDTO={}", orderDTO);
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (OrderPayStatusEnum.SUCCESS.getCode() == orderDTO.getPayStatus()) {
            logger.error("【订单支付完成】订单状态不正确,orderDTO={}", orderDTO);
            throw  new SellException(ResultEnum.ORDER_PAY_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(OrderPayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result =orderMasterRepository.save(orderMaster);
        if (result == null) {
            logger.error("【支付完成】支付状态更新失败orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage =orderMasterRepository.findAll(pageable);
        List<OrderMaster> orderMasterList =orderMasterPage.getContent();
        List<OrderDTO> orderDTOList =OrderMaster2OrderDTO.convert(orderMasterList);
        return new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
