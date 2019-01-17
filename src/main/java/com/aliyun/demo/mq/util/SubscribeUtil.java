package com.aliyun.demo.mq.util;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20170918.*;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Consumer操作类
 *
 * @author litinglan 2018/12/17 14:36
 */
public class SubscribeUtil {
    /**
     * 用于在服务器上创建对特定 Topic 的订阅关系
     * 新应用上线时，在注册 Topic 资源后，如果需要订阅消息，需要首先在 MQ 控制台或者调用该 API 创建注册对目标 Topic 的注册关系，然后才能使用注册的 CID 订阅消息
     *
     * @param topic      Topic名称
     * @param consumerId Consumer名称
     */
    public static void createConsumer(String topic, String consumerId) {
        try {
            if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(consumerId)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsSubscriptionCreateRequest request = new OnsSubscriptionCreateRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            //发布的 Topic 名称
            request.setTopic(topic);
            //需要创建的发布关系的 PID
            request.setConsumerId(consumerId);

            OnsSubscriptionCreateResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于删除之前使用 OnsSubscribeCreate 接口创建的订阅关系
     *
     * @param topic      Topic名称
     * @param consumerId Consumer名称
     */
    public static void deleteConsumer(String topic, String consumerId) {
        try {
            if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(consumerId)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsSubscriptionDeleteRequest request = new OnsSubscriptionDeleteRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            //发布的 Topic 名称
            request.setTopic(topic);
            //需要创建的发布关系的 PID
            request.setConsumerId(consumerId);

            OnsSubscriptionDeleteResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于查询目标 CID 的详细信息
     *
     * @param topic      Topic名称
     * @param consumerId Consumer名称
     */
    public static void queryConsumerById(String topic, String consumerId) {
        try {
            if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(consumerId)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsSubscriptionGetRequest request = new OnsSubscriptionGetRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            //发布的 Topic 名称
            request.setTopic(topic);
            //需要创建的发布关系的 PID
            request.setConsumerId(consumerId);

            OnsSubscriptionGetResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            List<OnsSubscriptionGetResponse.SubscribeInfoDo> subscribeInfoDoList = response.getData();
            subscribeInfoDoList.stream().forEach(subscribeInfoDo -> {
                //订阅信息在数据库中的索引编号,Long
                subscribeInfoDo.getId();
                //该 Topic 所在区域 ID，0-ALIYUN，1-CLOUD，2，3，4,Integer
                subscribeInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                subscribeInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                subscribeInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                subscribeInfoDo.getRegionName();
                //Topic 名称,String
                subscribeInfoDo.getTopic();
                //所有者编号，为阿里云的 uid,String
                subscribeInfoDo.getOwner();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                subscribeInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                subscribeInfoDo.getStatusName();
                //消费集群 ID,String
                subscribeInfoDo.getConsumerId();
                subscribeInfoDo.getCreateTime();
                subscribeInfoDo.getUpdateTime();
            });
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户所有的 CID 信息的列表
     */
    public static void queryConsumerList() {
        try {
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsSubscriptionListRequest request = new OnsSubscriptionListRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());

            OnsSubscriptionListResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            List<OnsSubscriptionListResponse.SubscribeInfoDo> subscribeInfoDoList = response.getData();
            subscribeInfoDoList.stream().forEach(subscribeInfoDo -> {
                //订阅信息在数据库中的索引编号,Long
                subscribeInfoDo.getId();
                //该 Topic 所在区域 ID，0-ALIYUN，1-CLOUD，2，3，4,Integer
                subscribeInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                subscribeInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                subscribeInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                subscribeInfoDo.getRegionName();
                //Topic 名称,String
                subscribeInfoDo.getTopic();
                //所有者编号，为阿里云的 uid,String
                subscribeInfoDo.getOwner();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                subscribeInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                subscribeInfoDo.getStatusName();
                //消费集群 ID,String
                subscribeInfoDo.getConsumerId();
                subscribeInfoDo.getCreateTime();
                subscribeInfoDo.getUpdateTime();
            });
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 Topic 或者 CID 信息搜索符合条件的 CID 信息
     * 一般用于不确定 CID 名称的时候，可以通过 Topic 来搜索 CID 信息
     *
     * @param keyWord 搜索的关键词，可以是 Topic 或者 CID
     */
    public static void queryConsumerBySelection(String keyWord) {
        try {
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsSubscriptionSearchRequest request = new OnsSubscriptionSearchRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            request.setSearch(keyWord);

            OnsSubscriptionSearchResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            List<OnsSubscriptionSearchResponse.SubscribeInfoDo> subscribeInfoDoList = response.getData();
            subscribeInfoDoList.stream().forEach(subscribeInfoDo -> {
                //订阅信息在数据库中的索引编号,Long
                subscribeInfoDo.getId();
                //该 Topic 所在区域 ID，0-ALIYUN，1-CLOUD，2，3，4,Integer
                subscribeInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                subscribeInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                subscribeInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                subscribeInfoDo.getRegionName();
                //Topic 名称,String
                subscribeInfoDo.getTopic();
                //所有者编号，为阿里云的 uid,String
                subscribeInfoDo.getOwner();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                subscribeInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                subscribeInfoDo.getStatusName();
                //消费集群 ID,String
                subscribeInfoDo.getConsumerId();
                subscribeInfoDo.getCreateTime();
                subscribeInfoDo.getUpdateTime();
            });
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于设置指定Consumer ID的读取消息的权限
     * 根据指定的 MQ 区域以及 Consumer ID 名称，可以配置该 Consumer ID 的读消息的开关，一般用于禁止特定 Consumer ID 读取消息的场景
     *
     * @param consumerId 指定Consumer名称
     * @param readEnable 读取消息开关
     */
    public static void configConsumerAccess(String consumerId, boolean readEnable) {
        try {
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsSubscriptionUpdateRequest request = new OnsSubscriptionUpdateRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            request.setConsumerId(consumerId);
            request.setReadEnable(readEnable);//设置读消息开关

            OnsSubscriptionUpdateResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
