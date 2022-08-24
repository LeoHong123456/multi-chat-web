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


import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.annotation.Log;
import com.javaweb.common.enums.BusinessType;
import com.javaweb.system.dto.RolePermissionDto;
import com.javaweb.system.entity.RoleMenu;
import com.javaweb.system.query.RoleMenuQuery;
import com.javaweb.system.service.IRoleMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaweb.common.common.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色菜单关系 控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-08
 */
@Controller
@RequestMapping("/rolemenu")
public class RoleMenuController extends BaseController {

    @Autowired
    private IRoleMenuService roleMenuService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:rolemenu:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(RoleMenuQuery query) {
        return roleMenuService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:rolemenu:add")
    @Log(title = "角色菜单关系", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody RoleMenu entity) {
        return roleMenuService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:rolemenu:update")
    @Log(title = "角色菜单关系", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody RoleMenu entity) {
        return roleMenuService.edit(entity);
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
        Map<String, Object> info = new HashMap<>();
        if (id != null && id > 0) {
            info = roleMenuService.info(id);
        }
        model.addAttribute("info", info);
        return super.edit(id, model);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @RequiresPermissions("sys:rolemenu:delete")
    @Log(title = "角色菜单关系", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return roleMenuService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:rolemenu:dall")
    @Log(title = "角色菜单关系", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return roleMenuService.deleteByIds(ids);
    }

    /**
     * 根据角色ID获取菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    @ResponseBody
    @GetMapping("/getRolePermissionByRoleId")
    public JsonResult getRolePermissionByRoleId(Integer roleId) {
        return roleMenuService.getRolePermissionByRoleId(roleId);
    }

    /**
     * 设置角色权限
     *
     * @param rolePermissionDto 角色菜单权限
     * @return
     */
    @ResponseBody
    @PostMapping("/setRolePermission")
    public JsonResult setRolePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return roleMenuService.setRolePermission(rolePermissionDto);
    }

}
