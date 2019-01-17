package com.aliyun.demo.mq.util;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20170918.*;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Producer操作类
 *
 * @author litinglan 2018/12/17 14:05
 */
public class PublishUtil {
    /**
     * 用于向 MQ 创建注册发布关系，并得到生成的 PID，用于生产环境发消息
     * 新创建资源，在创建 Topic 之后，需要注册对应的发布关系 (PID) 来发消息，调用 OnsPublishCreate 接口即可实现。
     * 注意：每个 Topic 只需要一个 PID 即可，不需要创建多个 PID，因为 PID 可以在多个应用中复用
     *
     * @param topic      Topic名称
     * @param producerId 创建指定的Producer
     */
    public static void createProducer(String topic, String producerId) {
        try {
            if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(producerId)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsPublishCreateRequest request = new OnsPublishCreateRequest();
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
            request.setProducerId(producerId);

            OnsPublishCreateResponse response = iAcsClient.getAcsResponse(request);
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
     * 删除已经发布过的 PID
     * 应用下线或者资源释放时，需要删除已经存在的发布关系，调用 OnsPublishDelete 接口删除，删除前提需要确保发布关系已经存在
     *
     * @param topic      Topic名称
     * @param producerId Producer
     */
    public static void deleteProducer(String topic, String producerId) {
        try {
            if (StringUtils.isEmpty(topic)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsPublishDeleteRequest request = new OnsPublishDeleteRequest();
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
            request.setProducerId(producerId);

            OnsPublishDeleteResponse response = iAcsClient.getAcsResponse(request);
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
     * 获取指定用户所拥有的所有发布关系
     * 一般用于展示用户所拥有的发布关系列表
     */
    public static void queryProducerList() {
        try {
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsPublishListRequest request = new OnsPublishListRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());

            OnsPublishListResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            List<OnsPublishListResponse.PublishInfoDo> publishInfoDoList = response.getData();
            publishInfoDoList.stream().forEach(publishInfoDo -> {
                //该发布信息在数据库中的 ID,Long
                publishInfoDo.getId();
                //该 Topic 所在区域 ID，0-ALIYUN，1-CLOUD，2，3，4,Integer
                publishInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                publishInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                publishInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                publishInfoDo.getRegionName();
                //Topic 名称,String
                publishInfoDo.getTopic();
                //Topic 所有者编号，为阿里云的 uid,String
                publishInfoDo.getOwner();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                publishInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                publishInfoDo.getStatusName();
                //Producer名称,String
                publishInfoDo.getProducerId();
                publishInfoDo.getCreateTime();
                publishInfoDo.getUpdateTime();
            });
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询指定 PID 的详细信息
     * 调用 OnsPublishGet 接口一般用于发布信息的展示和查询
     *
     * @param topic      Topic名称
     * @param producerId Producer名称
     */
    public static void queryProducerById(String topic, String producerId) {
        try {
            if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(producerId)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsPublishGetRequest request = new OnsPublishGetRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            request.setTopic(topic);
            request.setProducerId(producerId);

            OnsPublishGetResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            List<OnsPublishGetResponse.PublishInfoDo> publishInfoDoList = response.getData();
            publishInfoDoList.stream().forEach(publishInfoDo -> {
                //该发布信息在数据库中的 ID,Long
                publishInfoDo.getId();
                //该 Topic 所在区域 ID，0-ALIYUN，1-CLOUD，2，3，4,Integer
                publishInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                publishInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                publishInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                publishInfoDo.getRegionName();
                //Topic 名称,String
                publishInfoDo.getTopic();
                //Topic 所有者编号，为阿里云的 uid,String
                publishInfoDo.getOwner();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                publishInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                publishInfoDo.getStatusName();
                //Producer名称,String
                publishInfoDo.getProducerId();
                publishInfoDo.getCreateTime();
                publishInfoDo.getUpdateTime();
            });
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入的搜索条件，可以是 Topic 或者 appname 等字段
     * 一般用在用户账号下 PID 太多无法同时展现时，根据关键字搜索符合条件的发布关系
     *
     * @param keyWord 输入的搜索条件，可以是 Topic 或者 appname 等字段
     */
    public static void queryProducerBySelection(String keyWord) {
        try {
            if (StringUtils.isEmpty(keyWord)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsPublishSearchRequest request = new OnsPublishSearchRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            request.setSearch(keyWord);

            OnsPublishSearchResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            List<OnsPublishSearchResponse.PublishInfoDo> publishInfoDoList = response.getData();
            publishInfoDoList.stream().forEach(publishInfoDo -> {
                //该发布信息在数据库中的 ID,Long
                publishInfoDo.getId();
                //该 Topic 所在区域 ID，0-ALIYUN，1-CLOUD，2，3，4,Integer
                publishInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                publishInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                publishInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                publishInfoDo.getRegionName();
                //Topic 名称,String
                publishInfoDo.getTopic();
                //Topic 所有者编号，为阿里云的 uid,String
                publishInfoDo.getOwner();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                publishInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                publishInfoDo.getStatusName();
                //Producer名称,String
                publishInfoDo.getProducerId();
                publishInfoDo.getCreateTime();
                publishInfoDo.getUpdateTime();
            });
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
