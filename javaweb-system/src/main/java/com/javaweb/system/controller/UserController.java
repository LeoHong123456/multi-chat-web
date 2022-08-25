

package com.javaweb.system.controller;


import com.javaweb.common.annotation.Log;
import com.javaweb.common.enums.BusinessType;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.User;
import com.javaweb.system.query.UserQuery;
import com.javaweb.system.service.IUserRoleService;
import com.javaweb.system.service.IUserService;
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
 * 后台用户管理表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-01-28
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:user:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(UserQuery query) {
        return userService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody User entity) {
        return userService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:user:update")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody User entity) {
        return userService.edit(entity);
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
            info = userService.info(id);
            List<String> stringList = userRoleService.getRoleIdsByUserId(id);
            info.put("roleIds", StringUtils.join(stringList.toArray(), ","));
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
    @RequiresPermissions("sys:user:delete")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return userService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:user:dall")
    @Log(title = "用户管理", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return userService.deleteByIds(ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:user:status")
    @Log(title = "用户管理", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/setStatus")
    public JsonResult setStatus(@RequestBody User entity) {
        return userService.setStatus(entity);
    }

}
