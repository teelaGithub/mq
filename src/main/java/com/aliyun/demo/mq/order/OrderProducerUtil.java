package com.aliyun.demo.mq.order;

import com.aliyun.demo.mq.util.ProducerPropertiesUtil;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.order.OrderProducer;


/**
 * 顺序消息Producer
 * @author litinglan 2018/12/18 11:28
 */
public class OrderProducerUtil extends ProducerPropertiesUtil {
    private static OrderProducer producer;

    public static OrderProducer getIntance(){
        if (producer == null){
            synchronized (producer){
                if (producer == null){
                    // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
                    OrderProducer producer = ONSFactory.createOrderProducer(setProperties("producerId"));
                    producer.start();
                }
            }
        }
        return producer;
    }
}
