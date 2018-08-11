package com.liuyuan.sell.repository;

import com.liuyuan.sell.dataobject.SellInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellInfoRepository extends JpaRepository<SellInfo, String> {
    SellInfo findByOpenid(String openid);

}
