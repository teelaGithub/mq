package com.aliyun.demo.mq.util;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20170918.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Topic操作类
 *
 * @author litinglan 2018/12/17 10:44
 */
public class TopicUtil {
    /**
     * Topic 创建接口一般是在有新的 Topic 需求的情况下调用，例如发布新应用，业务逻辑需要新的 Topic
     * Topic 为跨地域全局唯一，不可重复创建，否则会创建失败，需要根据错误码进行处理
     * 本接口默认仅限主账号使用，RAM 子账号无法使用，除非该 RAM 子账号被授予了相关权限
     *
     * @param topic  Topic名称，全局唯一
     * @param remark 备注，可不填
     */
    public static void createTopic(String topic, String remark) {
        try {
            if (StringUtils.isEmpty(topic)) {
                return;
            }
            //启动客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //创建Topic请求参数
            OnsTopicCreateRequest request = new OnsTopicCreateRequest();
            request.setAcceptFormat(FormatType.JSON);
            //该请求来源，默认是从 POP 平台，非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取
            request.setOnsRegionId("");
            //用于 CSRF 校验，设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            //需要创建的 Topic 名称，Topic 名称全局唯一，不可重复
            request.setTopic(topic);
            //备注，可以不填
            request.setRemark(remark);
            //应用的 Key，公共云不填，聚石塔用户需要设置
//            request.setAppkey("");
            //应用名，公共云不填
//            request.setAppName("");

            OnsTopicCreateResponse response = iAcsClient.getAcsResponse(request);
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
     * Topic 删除接口一般是资源回收时使用，例如应用下线
     * Topic 删除时 MQ 后台需要做资源回收，速度较慢，因此不建议删除后立即又重新创建
     * 注意：删除 Topic 将导致该 Topic 下所有的发布订阅关系同时被清理，请慎重调用
     *
     * @param topic Topic名称
     */
    public static void deleteTopicById(String topic) {
        try {
            if (StringUtils.isEmpty(topic)) {
                return;
            }
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            OnsTopicDeleteRequest request = new OnsTopicDeleteRequest();
            request.setAcceptFormat(FormatType.JSON);
//            request.setOnsPlatform("");
            request.setOnsRegionId("");
            request.setPreventCache(System.currentTimeMillis());
            request.setTopic(topic);
            //todo
            request.setCluster("");

            OnsTopicDeleteResponse response = iAcsClient.getAcsResponse(request);
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
     * 查询账号下所有 Topic 的列表信息，一般用于生成资源列表的场景，并不查看具体信息
     *
     * @param topic Topic名称，可不填
     */
    public static void queryTopicList(String topic) {
        try {
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            OnsTopicListRequest request = new OnsTopicListRequest();
            request.setAcceptFormat(FormatType.JSON);
//            request.setOnsPlatform("");
            request.setOnsRegionId("");
            request.setPreventCache(System.currentTimeMillis());
            //不指定则查询全部
            request.setTopic(topic);

            OnsTopicListResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            //所有已发布的 Topic 信息列表
            List<OnsTopicListResponse.PublishInfoDo> publishInfoDoList = response.getData();
            publishInfoDoList.stream().forEach(publishInfoDo -> {
                //该 Topic 信息编号,Long
                publishInfoDo.getId();
                //该 Topic 所在区域 ID，0代表阿里云，1代表聚石塔,Integer
                publishInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                publishInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                publishInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                publishInfoDo.getRegionName();
                //opic 名称,String
                publishInfoDo.getTopic();
                //Topic 所有者编号，为阿里云的 uid,String
                publishInfoDo.getOwner();
                //所有关系编号（1为持有者，2表示可以发布，4表示可以订阅，6表示可以发布和订阅）,Integer
                publishInfoDo.getRelation();
                //所有关系名称，例如持有者、可订阅、可发布、可发布订阅,String
                publishInfoDo.getRelationName();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                publishInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                publishInfoDo.getStatusName();
                //null,String
                publishInfoDo.getAppkey();
                publishInfoDo.getCreateTime();
                publishInfoDo.getUpdateTime();
                publishInfoDo.getRemark();
            });

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询指定 Topic 的信息，属于精确查询
     * 查询账号下指定 Topic 的分布情况，并获取 Topic 当前的开通状态和权限关系等信息
     *
     * @param topic Topic名称
     */
    public static void queryTopicById(String topic) {
        try {
            if (StringUtils.isEmpty(topic)) {
                return;
            }
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            OnsTopicGetRequest request = new OnsTopicGetRequest();
            request.setAcceptFormat(FormatType.JSON);
//            request.setOnsPlatform("");
            request.setOnsRegionId("");
            request.setPreventCache(System.currentTimeMillis());
            request.setTopic(topic);

            OnsTopicGetResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            //所有已发布的 Topic 信息列表
            List<OnsTopicGetResponse.PublishInfoDo> publishInfoDoList = response.getData();
            publishInfoDoList.stream().forEach(publishInfoDo -> {
                //该 Topic 信息编号,Long
                publishInfoDo.getId();
                //该 Topic 所在区域 ID，0代表阿里云，1代表聚石塔,Integer
                publishInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                publishInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                publishInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                publishInfoDo.getRegionName();
                //opic 名称,String
                publishInfoDo.getTopic();
                //Topic 所有者编号，为阿里云的 uid,String
                publishInfoDo.getOwner();
                //所有关系编号（1为持有者，2表示可以发布，4表示可以订阅，6表示可以发布和订阅）,Integer
                publishInfoDo.getRelation();
                //所有关系名称，例如持有者、可订阅、可发布、可发布订阅,String
                publishInfoDo.getRelationName();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                publishInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                publishInfoDo.getStatusName();
                //null,String
                publishInfoDo.getAppkey();
                publishInfoDo.getCreateTime();
                publishInfoDo.getUpdateTime();
                publishInfoDo.getRemark();
            });

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据搜索条件搜索指定账号下所有的 Topic 信息列表
     * 该接口适用于查询 Topic 信息时没有精确的 Topic 名称的情况，一般用于获取整个 Topic 列表，在选择特定的 Topic 后基于 OnsTopicGet 接口精确查询
     *
     * @param keyWord 输入的搜索条件，可以是 Topic 或者 appname 等字段
     */
    public static void queryTopicBySelection(String keyWord) {
        try {
            if (StringUtils.isEmpty(keyWord)){
                return;
            }
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            OnsTopicSearchRequest request = new OnsTopicSearchRequest();
            request.setAcceptFormat(FormatType.JSON);
//            request.setOnsPlatform("");
            request.setOnsRegionId("");
            request.setPreventCache(System.currentTimeMillis());
            request.setSearch(keyWord);

            OnsTopicSearchResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            //所有已发布的 Topic 信息列表
            List<OnsTopicSearchResponse.PublishInfoDo> publishInfoDoList = response.getData();
            publishInfoDoList.stream().forEach(publishInfoDo -> {
                //该 Topic 信息编号,Long
                publishInfoDo.getId();
                //该 Topic 所在区域 ID，0代表阿里云，1代表聚石塔,Integer
                publishInfoDo.getChannelId();
                //该 Topic 所在区域名称，ALIYUN 代表阿里云，CLOUD 代表聚石塔,String
                publishInfoDo.getChannelName();
                //该 Topic 所在区域 ID，就是 ONSRegionList 方法获取的内容,Long
                publishInfoDo.getOnsRegionId();
                //该 Topic 所在区域名称,String
                publishInfoDo.getRegionName();
                //opic 名称,String
                publishInfoDo.getTopic();
                //Topic 所有者编号，为阿里云的 uid,String
                publishInfoDo.getOwner();
                //所有关系编号（1为持有者，2表示可以发布，4表示可以订阅，6表示可以发布和订阅）,Integer
                publishInfoDo.getRelation();
                //所有关系名称，例如持有者、可订阅、可发布、可发布订阅,String
                publishInfoDo.getRelationName();
                //当前状态编号（0 服务中 1 冻结 2 暂停）,Integer
                publishInfoDo.getStatus();
                //当前状态别名（服务中 冻结 暂停）,String
                publishInfoDo.getStatusName();
                //null,String
                publishInfoDo.getAppkey();
                publishInfoDo.getCreateTime();
                publishInfoDo.getUpdateTime();
                publishInfoDo.getRemark();
            });

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据搜索条件搜索指定账号下所有的 Topic 信息列表
     * 查询当前 Topic 下的消息总量以及最后更新时间，一般用于判断 Topic 资源的使用率。TopicStatus 接口返回当前服务器上该 Topic 下所有的消息数，以及最后消息写入时间
     *
     * @param topic Topic名称
     */
    public static void queryTopicStatus(String topic) {
        try {
            if (StringUtils.isEmpty(topic)) {
                return;
            }
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            OnsTopicStatusRequest request = new OnsTopicStatusRequest();
            request.setAcceptFormat(FormatType.JSON);
//            request.setOnsPlatform("");
            request.setOnsRegionId("");
            request.setPreventCache(System.currentTimeMillis());
            request.setTopic(topic);

            OnsTopicStatusResponse response = iAcsClient.getAcsResponse(request);
            //为公共参数，每个请求独一无二，用于排查定位问题
            response.getRequestId();
            //帮助链接
            response.getHelpUrl();
            //所有已发布的 Topic 信息列表
            OnsTopicStatusResponse.Data responseData = response.getData();
            //当前 Topic 的所有分区存在的消息数总和,Long
            responseData.getTotalCount();
            //当前 Topic 的最后更新时间,Long
            responseData.getLastTimeStamp();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定的 MQ 区域以及 Topic 名称，可以配置该 Topic 的读写开关。一般用于禁读或者禁写的场景
     * 本接口默认仅限主账号使用，RAM 子账号无法使用，除非该 RAM 子账号被授予了相关权限
     *
     * @param topic Topic名称
     * @param perm  设置该 Topic 的读写模式，6 代表同时支持读写，4 代表禁写，2 代表禁读
     */
    public static void configTopicAccess(String topic, Integer perm) {
        try {
            if (StringUtils.isEmpty(topic) || Objects.isNull(perm)) {
                return;
            }
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            OnsTopicUpdateRequest request = new OnsTopicUpdateRequest();
            request.setAcceptFormat(FormatType.JSON);
//            request.setOnsPlatform("");
            request.setOnsRegionId("");
            request.setPreventCache(System.currentTimeMillis());
            request.setTopic(topic);
            request.setPerm(perm);//6 代表同时支持读写，4 代表禁写，2 代表禁读

            OnsTopicUpdateResponse response = iAcsClient.getAcsResponse(request);
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
