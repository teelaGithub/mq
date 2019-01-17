package com.aliyun.demo.mq.transaction;

import com.aliyun.demo.mq.order.LocalTransactionCheckerImpl;
import com.aliyun.demo.mq.util.ProducerPropertiesUtil;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;

/**
 * @author litinglan 2018/12/18 11:50
 */
public class TransactionProducerUtil extends ProducerPropertiesUtil {
    private static TransactionProducer producer;

    public static TransactionProducer getInstance(){
        if (producer == null) {
            synchronized (producer) {
                if (producer == null) {
                    // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
                    TransactionProducer producer = ONSFactory.createTransactionProducer(setProperties("producerId"),new LocalTransactionCheckerImpl());
                    producer.start();
                }
            }
        }
        return producer;
    }
}
