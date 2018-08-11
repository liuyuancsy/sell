package com.liuyuan.sell;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] aa) {
        int i=0;
        AtomicInteger atomicInteger = new AtomicInteger();
        while(i<10) {
            i+=atomicInteger.addAndGet(1);
        }
        System.out.print(i);
    }
}
