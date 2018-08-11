package com.liuyuan.sell.utils;

import java.util.Random;

public class KeyUtil {
    /**
     *
     * @return 生成时间戳+固定长度随机数的字符串
     */
    public   static synchronized String  keyGenerate(){
        Long currentTimeMils = System.currentTimeMillis();
        Integer integer = new Random().nextInt(90000)+100000;
        return String.valueOf(currentTimeMils+integer);

    }


}
