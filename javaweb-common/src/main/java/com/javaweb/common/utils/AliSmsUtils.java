// +----------------------------------------------------------------------
// | JavaWeb_Layui混编版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站: http://www.javaweb.vip/
// +----------------------------------------------------------------------
// | 作者: 鲲鹏 <javaweb520@gmail.com>
// +----------------------------------------------------------------------
// | 免责声明:
// | 本软件框架禁止任何单位和个人用于任何违法、侵害他人合法利益等恶意的行为，禁止用于任何违
// | 反我国法律法规的一切平台研发，任何单位和个人使用本软件框架用于产品研发而产生的任何意外
// |  、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、附带
// | 或衍生的损失等)，本团队不承担任何法律责任。本软件框架已申请版权保护，任何组织、单位和个
// | 人不得有任何侵犯我们的版权的行为(包括但不限于分享、转售、恶意传播，开源等等)，否则产生
// | 的一切后果和损失由侵权者全部承担，本软件框架只能用于公司和个人内部的法律所允许的合法合
// | 规的软件产品研发，详细声明内容请阅读《框架免责声明》附件；
// +----------------------------------------------------------------------

package com.javaweb.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.javaweb.common.config.AliSmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里短信发送类
 */
@Configuration
public class AliSmsUtils {

    // 注册阿里短信配置
    @Autowired
    private AliSmsConfig aliSmsConfig;

    /**
     * 发送短信验证码
     *
     * @param code   6位数验证码
     * @param mobile 手机号码
     * @return
     */
    public boolean sendSms(String code, String mobile) {
        Map<String, String> map = new HashMap<>();
        map.put("RegionId", aliSmsConfig.getRegionId());
        map.put("PhoneNumbers", mobile);
        map.put("SignName", aliSmsConfig.getSignName());
        map.put("TemplateCode", aliSmsConfig.getTemplateCode());
        map.put("TemplateParam", "{\"code\":\"" + code + "\"}");
        String result = this.sendSdk(map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.get("Message").equals("OK");
    }

    /**
     * 发送短信验证码
     *
     * @param map 参数
     * @return
     */
    public boolean sendSms(Map<String, String> map) {
        String result = this.sendSdk(map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.get("Message").equals("OK");
    }

    /**
     * 调用发送短信SDK
     *
     * @param map
     * @return
     */
    private String sendSdk(Map<String, String> map) {
        DefaultProfile profile = DefaultProfile.getProfile(aliSmsConfig.getRegionId(), aliSmsConfig.getAccessKeyId(), aliSmsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entity : map.entrySet()) {
                request.putQueryParameter(entity.getKey(), entity.getValue());
            }
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
