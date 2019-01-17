package com.aliyun.demo.mq.normal;

import com.aliyun.demo.mq.util.ProducerPropertiesUtil;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;

import java.util.Properties;


/**
 * 普通、定时、延时消息Producer
 * 定时、延时消息：Message.setStartDeliverTime()
 *
 * @author litinglan 2018/12/18 11:24
 */
public class ProducerUtil extends ProducerPropertiesUtil {
    private static Producer producer;

    /**
     * 获取普通消息、定时、延时消息
     *
     * @return
     */
    public static Producer getIntance() {
        if (producer == null) {
            synchronized (producer) {
                if (producer == null) {
                    // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
                    Producer producer = ONSFactory.createProducer(setProperties("producerId"));
                    producer.start();
                }
            }
        }
        return producer;
    }

    public Producer getInstanceById(String producerId){
        Properties properties = setProperties(producerId);
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        return producer;
    }
}
