package com.aliyun.demo.mq.util;

/**
 * @author litinglan 2018/12/17 13:40
 */
public class Test implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread());
        RegionUtil.getRegionList();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
