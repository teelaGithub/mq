package com.aliyun.demo.mq.util;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20170918.OnsRegionListRequest;
import com.aliyuncs.ons.model.v20170918.OnsRegionListResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MQ 目前开放服务的 Region 列表
 *
 * @author litinglan 2018/12/14 16:52
 */
public class RegionUtil {
    /**
     * 获取Region集合
     */
    public static void getRegionList() {
        try {
            //启动Open API的客户端
            IAcsClient iAcsClient = IAcsClientUtil.getDefaultClient();
            //请求参数
            OnsRegionListRequest request = new OnsRegionListRequest();
            request.setAcceptFormat(FormatType.JSON);
            //用于 CSRF 校验,设置为系统当前时间即可
            request.setPreventCache(System.currentTimeMillis());
            //该请求来源,默认是从 POP（阿里云 Open API 平台） 平台,非必填
//          request.setOnsPlatform("POP");
            //当前查询 MQ 所在地域,通过 OnsRegionList 进行获取,非必填
//          request.setOnsRegionId("");

            OnsRegionListResponse response = iAcsClient.getAcsResponse(request);
            List<OnsRegionListResponse.RegionDo> regionDoList = response.getData();
            regionDoList.stream().forEach(regionDo -> {
                //数据库中编号,Long
                regionDo.getId();
                //regionId 名称,String
                regionDo.getOnsRegionId();
                //Region 别名,String
                regionDo.getRegionName();
                //所在 Channel 编号,Long
                regionDo.getChannelId();
                //所在 Channel 别名,String
                regionDo.getChannelName();
                //创建时间,Long
                regionDo.getCreateTime();
                //最后更新时间,Long
                regionDo.getUpdateTime();
            });

        } catch (ClientException e) {
            System.out.println(e.getErrCode() + ":" + e.getErrMsg() + ",requestId:" + e.getRequestId());
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++) {
//
//            Thread thread = new Thread(new Test());
//            thread.start();
//            RegionUtil.getRegionList();
//        }
        List<String> strings = Arrays.asList("a", "b", "c", "a", "b", "c", "d");
        List<String> a = strings.stream().filter(s -> s.equals("a")).collect(Collectors.toList());
        System.out.println(a);
        System.out.println(strings);
    }
}
