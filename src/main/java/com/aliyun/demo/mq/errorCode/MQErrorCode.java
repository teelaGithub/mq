package com.aliyun.demo.mq.errorCode;

/**
 * @author litinglan 2018/12/17 11:05
 */
public enum MQErrorCode {
    //通过阿里云工单联系 MQ 技术人员
    ONS_SYSTEM_ERROR("MQ 后端异常")
    //请找 MQ 技术支持确认该接口的开通情况
    ,ONS_SERVICE_UNSUPPORTED("当前调用在对应的 Region 区域不支持")
    //联系 MQ 技术人员处理
    ,ONS_INVOKE_ERROR("Open API 接口调用失败")
    //请参考 API 手册检查各个参数传入是否合法
    ,BIZ_FIELD_CHECK_INVALID("参数检验失败")
    //请检查 Topic 输入是否合法，或者是否创建过
    ,BIZ_TOPIC_NOT_FOUND("Topic 没有找到")
    //请检查 CID 是否创建过，或者查询条件错误
    ,BIZ_SUBSCRIPTION_NOT_FOUND("目标订阅关系 CID 找不到")
    //更换 PID 的名称重试请求
    ,BIZ_PUBLISHER_EXISTED("指定 PID 已经存在")
    //更换 CID 的名称重试请求
    ,BIZ_SUBSCRIPTION_EXISTED("指定 CID 已经存在")
    //请确保消费端在线，然后重试请求
    ,BIZ_CONSUMER_NOT_ONLINE("指定 CID 的客户端不在线")
    //请检查查询条件，并确认查询范围内是否发过消息
    ,BIZ_NO_MESSAGE("当前查询条件没有匹配消息")
    //确认请求的 Region 参数是否合法
    ,BIZ_REGION_NOT_FOUND("请求的 Region 找不到")
    //更改 Topic 名称重试请求
    ,BIZ_TOPIC_EXISTED("指定 Topic 已经存在")
    //更换 PID 重试请求
    ,BIZ_PRODUCER_ID_BELONG_TO_OTHER_USER("当前 PID 已经被其他用户占用")
    //更换 CID 重试请求
    ,BIZ_CONSUMER_ID_BELONG_TO_OTHER_USER("当前 CID 已经被其他用户占用")
    //确认 PID 是否存在，或者请求条件错误
    ,BIZ_PUBLISH_INFO_NOT_FOUND("请求的 PID 没有找到")
    //核实请求参数，重试或者先查询
    ,EMPOWER_EXIST_ERROR("当前授权关系已经存在")
    //确认资源所属关系
    ,EMPOWER_OWNER_CHECK_ERROR("当前用户不是授权 Topic 的 Owner")
    ;

    private  int code;
    private String msg;
    MQErrorCode(String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
