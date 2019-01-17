package com.aliyun.demo.mq.order;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;

/**
 * 处理异常情况下事务消息的回查
 *
 * (1) 检查该 Half 消息对应的本地事务的状态（commited or rollback）；
 * (2) 向 MQ Broker 提交该 Half 消息本地事务的状态。
 * @author litinglan 2018/12/18 11:52
 */
public class LocalTransactionCheckerImpl implements LocalTransactionChecker {
    @Override
    public TransactionStatus check(Message message) {
        return null;
    }
}
