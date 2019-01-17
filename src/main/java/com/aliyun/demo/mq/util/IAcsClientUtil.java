package com.aliyun.demo.mq.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云Open API 的客户端
 *
 * @author litinglan 2018/12/14 17:50
 */
public class IAcsClientUtil {

    private static IAcsClient iAcsClient;

    /**
     * 启动默认的Open API客户端
     * 单例
     *
     * @return IAcsClient
     */
    public static IAcsClient getDefaultClient() {
        if (iAcsClient == null){
            synchronized (IAcsClient.class){
                if (iAcsClient == null){
                    //Open API 的接入点，设置为目标 Region,指的是 API 的网关所在地域
                    String regionId = "cn-shenzhen";
                    //接入点名称，同 RegionId 一致即可
                    //Open API 根据设置的接入点来调用对应的后端服务
                    String endPointName = "cn-shenzhen";
                    //对应 endPoint 接入点的接入点域名,规则是 ons.${RegionId}.aliyuncs.com,该 domain 对应的地域必须和 regionId 对应的地域一致
                    //Open API 根据设置的访问域名来调用对应的后端服务
                    String domain = "ons.cn-shenzhen.aliyuncs.com";
                    //通过 Open API 访问的云产品名称，此处设置为 Ons 即可
                    String productName = "ons";
                    String accessKey = "LTAId3qfbG4WWqWb";
                    String secretKey = "****";

                    //注意：Open API 使用时需要启动 Open API 的客户端，而客户端启动时需要设置接入点和 AccessKey，SecretKey 等参数信息
                    //根据自己需要访问的地域选择 Region，并设置对应的接入点
                    DefaultProfile.addEndpoint(regionId, productName, domain);

                    IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
                    iAcsClient = new DefaultAcsClient(profile);
                }
            }
        }
        return iAcsClient;
    }

    /**
     * 启动指定regionId的客户端
     *
     * @param regionId     API 的网关所在地域
     * @param endPointName 接入点名称，同 RegionId 一致即可
     * @return
     */
    public static IAcsClient getClientByField(String regionId, String endPointName) {
        String productName = "ons";
        String accessKey = "";
        String secretKey = "";

        DefaultProfile.addEndpoint(regionId, productName, endPointName);
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
        IAcsClient iAcsClient = new DefaultAcsClient(profile);
        return iAcsClient;
    }
}
