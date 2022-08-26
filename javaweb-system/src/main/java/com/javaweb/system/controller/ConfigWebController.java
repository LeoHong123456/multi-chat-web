

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
