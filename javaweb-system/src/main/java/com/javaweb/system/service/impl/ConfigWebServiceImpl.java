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

package com.javaweb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.entity.ConfigData;
import com.javaweb.system.mapper.ConfigDataMapper;
import com.javaweb.system.service.IConfigWebService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfigWebServiceImpl implements IConfigWebService {

    @Autowired
    private ConfigDataMapper configMapper;

    /**
     * @param map 参数
     * @return
     */
    @Override
    public JsonResult configEdit(Map<String, Object> map) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            System.out.println("KEY:" + key + ",值：" + value);

            if (key.contains("checkbox")) {
                // 复选框
                String[] item = key.split("__");
                key = item[0];
            } else if (key.contains("upimage")) {
                // 单图上传
                String[] item = key.split("__");
                key = item[0];
                if (value.contains(CommonConfig.imageURL)) {
                    value = value.replaceAll(CommonConfig.imageURL, "");
                }
            } else if (key.contains("upimgs")) {
                // 多图上传
                String[] item = key.split("__");
                key = item[0];

                String[] stringsVal = value.split(",");
                List<String> stringList = new ArrayList<>();
                for (String s : stringsVal) {
                    if (s.contains(CommonConfig.imageURL)) {
                        stringList.add(s.replaceAll(CommonConfig.imageURL, ""));
                    } else {
                        // 已上传图片
                        stringList.add(s.replaceAll(CommonConfig.imageURL, ""));
                    }
                }
                value = StringUtils.join(stringList, ",");
            } else if (key.contains("ueditor")) {
                String[] item = key.split("__");
                key = item[0];
                // 处理富文本信息

            }
            // 更新信息
            QueryWrapper<ConfigData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tag", key);
            ConfigData config = configMapper.selectOne(queryWrapper);
            if (config == null) {
                continue;
            }
            config.setValue(value);
            configMapper.updateById(config);
        }
        return JsonResult.success();
    }
}
