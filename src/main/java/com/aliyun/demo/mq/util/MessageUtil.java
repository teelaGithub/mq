package com.aliyun.demo.mq.util;

import com.aliyun.openservices.ons.api.*;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author litinglan 2018/12/18 10:06
 */
public class MessageUtil {
    /**
     * 发送同步消息
     */
    public static void sendMsg() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId, "");
        properties.setProperty(PropertyKeyConst.AccessKey, "");
        properties.setProperty(PropertyKeyConst.SecretKey, "");
        properties.setProperty(PropertyKeyConst.ONSAddr, "");//接入点,根据不同Region进行配置
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "");

        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();

        Message message = new Message();
        try {
            message.setTopic("");
            // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
//            message.setTag("");
            message.setBody("".getBytes("UTF-8"));
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
            // 注意：不设置也不会影响消息正常收发
//            message.setKey("");

            SendResult sendResult = producer.send(message);
            // 同步发送消息，只要不抛异常就是成功
            if (sendResult != null) {

            }
        } catch (UnsupportedEncodingException e) {//转码异常
            e.printStackTrace();
        } catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            e.printStackTrace();
        } finally {
            // 在应用退出前，销毁 Producer 对象
            // 注意：如果不销毁也没有问题
            producer.shutdown();
        }
    }

    /**
     * 发送异步消息
     */
    public static void sendAsysn() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId, "");
        properties.setProperty(PropertyKeyConst.AccessKey, "");
        properties.setProperty(PropertyKeyConst.SecretKey, "");
        properties.setProperty(PropertyKeyConst.ONSAddr, "");//接入点,根据不同Region进行配置
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "");

        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();

        Message message = new Message();
        try {
            message.setTopic("");
            // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
//            message.setTag("");
            message.setBody("".getBytes("UTF-8"));
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
            // 注意：不设置也不会影响消息正常收发
//            message.setKey("");

            //发送异步消息
            producer.sendAsync(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    // 消费发送成功
                }

                @Override
                public void onException(OnExceptionContext onExceptionContext) {
                    // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                }
            });
            // 在 callback 返回之前即可取得 msgId
            message.getMsgID();
        } catch (UnsupportedEncodingException e) {//转码异常
            e.printStackTrace();
        } catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            e.printStackTrace();
        } finally {
            // 在应用退出前，销毁 Producer 对象
            // 注意：如果不销毁也没有问题
            producer.shutdown();
        }
    }

    /**
     * 发送单向消息
     */
    public static void sendOneway() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId, "");
        properties.setProperty(PropertyKeyConst.AccessKey, "");
        properties.setProperty(PropertyKeyConst.SecretKey, "");
        properties.setProperty(PropertyKeyConst.ONSAddr, "");//接入点,根据不同Region进行配置
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "");

        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();

        Message message = new Message();
        try {
            message.setTopic("");
            // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
//            message.setTag("");
            message.setBody("".getBytes("UTF-8"));
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
            // 注意：不设置也不会影响消息正常收发
//            message.setKey("");

            // 由于在 oneway 方式发送消息时没有请求应答处理，一旦出现消息发送失败，则会因为没有重试而导致数据丢失。若数据不可丢，建议选用可靠同步或可靠异步发送方式。
            producer.sendOneway(message);
        } catch (UnsupportedEncodingException e) {//转码异常
            e.printStackTrace();
        } finally {
            // 在应用退出前，销毁 Producer 对象
            // 注意：如果不销毁也没有问题
            producer.shutdown();
        }
    }
}
