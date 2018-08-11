package com.liuyuan.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SellInfo {
    @Id
    private String id;

    private String username;

    private String password;

    private String openid;
}
