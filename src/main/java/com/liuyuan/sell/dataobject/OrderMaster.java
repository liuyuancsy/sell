package com.liuyuan.sell.dataobject;

import com.liuyuan.sell.enums.OrderPayStatusEnum;
import com.liuyuan.sell.enums.OrderStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();
    private Integer payStatus=OrderPayStatusEnum.WAIT.getCode();
    private Date createTime;
    private Date updateTime;



}
