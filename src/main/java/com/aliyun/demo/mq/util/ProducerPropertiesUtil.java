package com.aliyun.demo.mq.util;

import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * @author litinglan 2018/12/18 11:23
 */
public class ProducerPropertiesUtil {
    public static Properties setProperties(String producerId){
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId, producerId);
        properties.setProperty(PropertyKeyConst.AccessKey, "");
        properties.setProperty(PropertyKeyConst.SecretKey, "");
        properties.setProperty(PropertyKeyConst.ONSAddr, "");//接入点,根据不同Region进行配置
        //设置发送超时时间，单位毫秒
//        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "");
        return properties;
    }
}
