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

package com.javaweb.generator.controller;


import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.ConvertUtil;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.annotation.Log;
import com.javaweb.common.enums.BusinessType;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.generator.dto.GeneratorTableDto;
import com.javaweb.generator.dto.ImportTableDto;
import com.javaweb.generator.entity.GenTable;
import com.javaweb.generator.query.GenTableQuery;
import com.javaweb.generator.service.IGenTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaweb.common.common.BaseController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成 控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-10
 */
@Controller
@RequestMapping("/gentable")
public class GenTableController extends BaseController {

    @Autowired
    private IGenTableService genTableService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
//    @RequiresPermissions("sys:gentable:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(GenTableQuery query) {
        return genTableService.getList(query);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
//    @RequiresPermissions("sys:gentable:update")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody GenTable entity) {
        return genTableService.edit(entity);
    }

    /**
     * 获取记录详情
     *
     * @param id    记录ID
     * @param model 模型
     * @return
     */
    @Override
    public String edit(Integer id, Model model) {
        // 根据业务ID获取数据表信息
        GenTable table = genTableService.selectGenTableById(id);
        model.addAttribute("info", table);
        return this.render();
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
//    @RequiresPermissions("sys:gentable:delete")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return genTableService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
//    @RequiresPermissions("sys:gentable:batchDelete")
    @Log(title = "代码生成", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return genTableService.deleteByIds(ids);
    }


    /**
     * 导入数据表
     *
     * @return
     */
    @GetMapping("/importTable")
    public String importTable() {
        return this.render();
    }

    /**
     * 获取数据库表
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/genDbTableList")
    public JsonResult genDbTableList(GenTableQuery query) {
        return genTableService.genDbTableList(query);
    }

    /**
     * 导入数据表
     *
     * @param importTableDto 数据表名(多个逗号","分割)
     * @return
     */
    @Log(title = "导入数据表", businessType = BusinessType.IMPORT)
    @ResponseBody
    @PostMapping("/importTableSave")
    public JsonResult importTableSave(@RequestBody ImportTableDto importTableDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        String[] tableNames = ConvertUtil.toStrArray(importTableDto.getTables());
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return JsonResult.success();
    }

    /**
     * 代码生成
     *
     * @param generatorTableDto 数据表名(多个逗号","分割)
     * @return
     * @throws IOException
     */
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @ResponseBody
    @PostMapping("/batchGenCode")
    public JsonResult batchGenCode(@RequestBody GeneratorTableDto generatorTableDto) throws IOException {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        String[] tableNames = ConvertUtil.toStrArray(generatorTableDto.getTables());
        return genTableService.generatorCode(tableNames);
    }

    /**
     * 批量生成
     *
     * @param map 记录ID
     * @return
     */
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @ResponseBody
    @PostMapping("/batchGenerator")
    public JsonResult batchGenerator(@RequestBody Map<String, String> map) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        String ids = map.get("ids");
        if (StringUtils.isEmpty(ids)) {
            return JsonResult.error("数据表ID不能为空");
        }
        String[] tableIds = ids.split(",");
        List<String> stringList = new ArrayList<>();
        for (String tableId : tableIds) {
            GenTable genTable = genTableService.getById(Integer.valueOf(tableId));
            if (genTable == null) {
                continue;
            }
            stringList.add(genTable.getTableName());
        }
        String[] tableNames = stringList.toArray(new String[stringList.size()]);
        return genTableService.generatorCode(tableNames);
    }

}
