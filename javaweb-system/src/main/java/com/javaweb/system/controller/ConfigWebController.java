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

package com.javaweb.system.controller;

import com.javaweb.common.common.BaseController;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.ServletUtils;
import com.javaweb.system.entity.ConfigData;
import com.javaweb.system.service.IConfigDataService;
import com.javaweb.system.service.IConfigWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/configweb")
public class ConfigWebController extends BaseController {

    @Autowired
    private IConfigDataService configService;
    @Autowired
    private IConfigWebService configWebService;

    /**
     * 获取系统配置信息
     *
     * @param model
     * @return
     */
    @Override
    public String index(Model model) {
        String tabId = ServletUtils.getParameter("tabId");
        Integer configId = tabId == null ? 1 : Integer.valueOf(tabId);
        model.addAttribute("tabId", configId);

        // 获取当前类型配置列表
        List<ConfigData> configList = configService.getConfigListByGroupId(configId);
        model.addAttribute("configList", configList);
        return this.render();
    }

    /**
     * 编辑配置参数
     *
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping("/configEdit")
    public JsonResult configEdit(@RequestBody Map<String, Object> map) {
        return configWebService.configEdit(map);
    }

}
