package com.liuyuan.sell.controller;

import com.liuyuan.sell.config.ProjectUrlConfig;
import com.liuyuan.sell.constant.CookieConstant;
import com.liuyuan.sell.constant.RedisConstant;
import com.liuyuan.sell.enums.ResultEnum;
import com.liuyuan.sell.exception.SellException;
import com.liuyuan.sell.service.SellInfoService;
import com.liuyuan.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller/")
public class SellerController {
    @Autowired
    private SellInfoService sellInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private  ProjectUrlConfig projectUrlConfig;
    //卖家登录login
    @GetMapping("login")
    public ModelAndView login(Map<String, Object> map,HttpServletResponse response,
                              @RequestParam("openId") String openId) {

        try {
           sellInfoService.findByOpenid(openId);
        } catch (SellException s) {
            log.info("【登录】失败信息={}",s.getMessage());
            map.put("msg", s.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        String token = UUID.randomUUID().toString();
        //保存token至redis
        log.info("保存token至redis");
        log.info("token={}",token);
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),
                openId, 7200, TimeUnit.SECONDS);
        Integer expire = RedisConstant.EXPIRE;
//        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        //3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        log.info("保存token至cookie成功，开始跳转到列表页");
        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"seller/order/list");
    }

    //卖家登出
    @RequestMapping("logout")
    public ModelAndView logout(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        //从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        //清除redis
        if(cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,
                    cookie.getValue()));
        }
        //清除cookie
        //3. 清除cookie
        CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        log.info("清除cookie成功，开始跳转到列表页");
        return new ModelAndView("common/success",map);
    }

}
