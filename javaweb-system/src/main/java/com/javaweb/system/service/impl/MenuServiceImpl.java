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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.DateUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.constant.MenuConstant;
import com.javaweb.system.entity.Menu;
import com.javaweb.system.mapper.MenuMapper;
import com.javaweb.system.query.MenuQuery;
import com.javaweb.system.service.IMenuService;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.menu.MenuListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-07
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MenuQuery menuQuery = (MenuQuery) query;
        // 查询条件
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        // 上级ID
        if (StringUtils.isNotNull(menuQuery.getPid()) && menuQuery.getPid() > 0) {
            queryWrapper.eq("pid", menuQuery.getPid());
        } else {
            queryWrapper.eq("pid", 0);
        }
        // 菜单名称
        if (!StringUtils.isEmpty(menuQuery.getName())) {
            queryWrapper.like("name", menuQuery.getName());
        }
        // 类型：1模块 2导航 3菜单 4节点
        if (menuQuery.getType() != null && menuQuery.getType() > 0) {
            queryWrapper.eq("type", menuQuery.getType());
        }
        // 是否显示：1显示 2不显示
        if (menuQuery.getStatus() != null && menuQuery.getStatus() > 0) {
            queryWrapper.eq("status", menuQuery.getStatus());
        }
        // 是否公共：1是 2否
        if (menuQuery.getIsPublic() != null && menuQuery.getIsPublic() > 0) {
            queryWrapper.eq("is_public", menuQuery.getIsPublic());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询数据
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        List<MenuListVo> menuListVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                MenuListVo menuListVo = new MenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, menuListVo);
                // 类型描述
                if (menuListVo.getType() != null && menuListVo.getType() > 0) {
                    menuListVo.setTypeName(MenuConstant.MENU_TYPE_LIST.get(menuListVo.getType()));
                }
                // 是否显示描述
                if (menuListVo.getStatus() != null && menuListVo.getStatus() > 0) {
                    menuListVo.setStatusName(MenuConstant.MENU_STATUS_LIST.get(menuListVo.getStatus()));
                }
                // 是否公共描述
                if (menuListVo.getIsPublic() != null && menuListVo.getIsPublic() > 0) {
                    menuListVo.setIsPublicName(MenuConstant.MENU_ISPUBLIC_LIST.get(menuListVo.getIsPublic()));
                }
                // 创建人名称
                if (menuListVo.getCreateUser() != null && menuListVo.getCreateUser() > 0) {
                    menuListVo.setCreateUserName(UserUtils.getName((menuListVo.getCreateUser())));
                }
                // 更新人名称
                if (menuListVo.getUpdateUser() != null && menuListVo.getUpdateUser() > 0) {
                    menuListVo.setUpdateUserName(UserUtils.getName((menuListVo.getUpdateUser())));
                }
                // 是否有子级
                if (item.getType() < 4) {
                    menuListVo.setHaveChild(true);
                }
                menuListVoList.add(menuListVo);
            });
        }
        return JsonResult.success("操作成功", menuListVoList);
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Menu entity = (Menu) super.getInfo(id);
        // 获取权限节点ID
        List<String> stringList = new ArrayList<>();
        if (entity.getType().equals(3)) {
            List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getPid, entity.getId())
                    .eq(Menu::getMark, 1));
            if (!menuList.isEmpty()) {
                menuList.forEach(item -> {
                    stringList.add(item.getSort().toString());
                });
            }
            // 字符串拼接
            entity.setFuncIds(StringUtils.join(stringList.toArray(), ","));
        }
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Menu entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (entity == null) {
            return JsonResult.error("实体对象不存在");
        }
        boolean result = false;
        if (entity.getId() != null && entity.getId() > 0) {
            // 修改记录
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
            result = this.updateById(entity);
        } else {
            // 新增记录
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
            entity.setMark(1);
            result = this.save(entity);
        }
        if (!result) {
            return JsonResult.error();
        }

        // 权限节点处理
        if (!StringUtils.isEmpty(entity.getUrl()) && !entity.getUrl().equals("#")) {
            // 删除已有节点
            menuMapper.delete(new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getPid, entity.getId())
                    .eq(Menu::getType, 4));
            // 节点描述
            String title = entity.getName().replace("管理", "");
            String[] urlItem = entity.getUrl().split("/");
            // 模块名
            String module = urlItem[1];
            if (!StringUtils.isEmpty(entity.getFuncIds())) {
                String[] item = entity.getFuncIds().split(",");
                for (String s : item) {
                    Menu menu = new Menu();
                    menu.setPid(entity.getId());
                    menu.setType(4);
                    menu.setStatus(1);
                    menu.setIsPublic(2);
                    menu.setSort(Integer.valueOf(s));
                    menu.setCreateUser(ShiroUtils.getUserId());
                    menu.setCreateTime(DateUtils.now());
                    menu.setUpdateUser(ShiroUtils.getUserId());
                    menu.setUpdateTime(DateUtils.now());
                    menu.setMark(1);
                    if (Integer.valueOf(s).equals(1)) {
                        // 查询
                        menu.setName(String.format("查询%s", title));
                        menu.setUrl(String.format("/%s/list", module));
                        menu.setPermission(String.format("sys:%s:list", module));
                    } else if (Integer.valueOf(s).equals(5)) {
                        // 添加
                        menu.setName(String.format("添加%s", title));
                        menu.setUrl(String.format("/%s/add", module));
                        menu.setPermission(String.format("sys:%s:list", module));
                    } else if (Integer.valueOf(s).equals(10)) {
                        // 修改
                        menu.setName(String.format("修改%s", title));
                        menu.setUrl(String.format("/%s/update", module));
                        menu.setPermission(String.format("sys:%s:update", module));
                    } else if (Integer.valueOf(s).equals(15)) {
                        // 删除
                        menu.setName(String.format("删除%s", title));
                        menu.setUrl(String.format("/%s/delete", module));
                        menu.setPermission(String.format("sys:%s:delete", module));
                    } else if (Integer.valueOf(s).equals(20)) {
                        // 状态
                        menu.setName("设置状态");
                        menu.setUrl(String.format("/%s/setStatus", module));
                        menu.setPermission(String.format("sys:%s:status", module));
                    } else if (Integer.valueOf(s).equals(25)) {
                        // 批量删除
                        menu.setName("批量删除");
                        menu.setUrl(String.format("/%s/batchDelete", module));
                        menu.setPermission(String.format("sys:%s:dall", module));
                    } else if (Integer.valueOf(s).equals(30)) {
                        // 全部展开
                        menu.setName("全部展开");
                        menu.setUrl(String.format("/%s/expand", module));
                        menu.setPermission(String.format("sys:%s:expand", module));
                    } else if (Integer.valueOf(s).equals(35)) {
                        // 全部折叠
                        menu.setName("全部折叠");
                        menu.setUrl(String.format("/%s/collapse", module));
                        menu.setPermission(String.format("sys:%s:collapse", module));
                    } else if (Integer.valueOf(s).equals(40)) {
                        // 添加子级
                        menu.setName("添加子级");
                        menu.setUrl(String.format("/%s/addz", module));
                        menu.setPermission(String.format("sys:%s:addz", module));
                    } else if (Integer.valueOf(s).equals(45)) {
                        // 导出数据
                        menu.setName("导出数据");
                        menu.setUrl(String.format("/%s/export", module));
                        menu.setPermission(String.format("sys:%s:export", module));
                    } else if (Integer.valueOf(s).equals(50)) {
                        // 导入数据
                        menu.setName("导入数据");
                        menu.setUrl(String.format("/%s/import", module));
                        menu.setPermission(String.format("sys:%s:import", module));
                    } else if (Integer.valueOf(s).equals(55)) {
                        // 分配权限
                        menu.setName("分配权限");
                        menu.setUrl(String.format("/%s/permission", module));
                        menu.setPermission(String.format("sys:%s:permission", module));
                    } else if (Integer.valueOf(s).equals(60)) {
                        // 重置密码
                        menu.setName("重置密码");
                        menu.setUrl(String.format("/%s/resetPwd", module));
                        menu.setPermission(String.format("sys:%s:resetPwd", module));
                    }
                    // 创建权限节点
                    menuMapper.insert(menu);
                }
            }
        }
        return JsonResult.success("操作成功");
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        Menu entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Menu entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 获取导航菜单
     *
     * @param userId 人员ID
     * @return
     */
    @Override
    public List<MenuListVo> getNavbarMenu(Integer userId) {
        if (userId.equals(1)) {
            // 管理员(管理员拥有全部权限)
            List<MenuListVo> menuListVoList = getPermissionMenuAll(0);
            return menuListVoList;
        } else {
            // 非管理员
            List<MenuListVo> menuListVoList = getPermissionMenuList(0);
            return menuListVoList;
        }
    }

    /**
     * 遍历获取权限菜单
     *
     * @param pid 上级ID
     * @return
     */
    private List<MenuListVo> getPermissionMenuList(Integer pid) {
        List<MenuListVo> menuListVoList = menuMapper.getPermissionMenuList(ShiroUtils.getUserId(), pid);
        if (!menuListVoList.isEmpty()) {
            menuListVoList.forEach(item -> {
                List<MenuListVo> menuList = getPermissionMenuList(item.getId());
                if (!menuList.isEmpty()) {
                    item.setChildren(menuList);
                }
            });
        }
        return menuListVoList;
    }

    /**
     * 根据父级ID获取子级菜单
     *
     * @param pid 上级ID
     * @return
     */
    public List<MenuListVo> getPermissionMenuAll(Integer pid) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu> menuList = list(queryWrapper);
        List<MenuListVo> menuListVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                MenuListVo menuListVo = new MenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, menuListVo);
                List<MenuListVo> childrenList = getPermissionMenuAll(item.getId());
                menuListVo.setChildren(childrenList);
                menuListVoList.add(menuListVo);
            });
        }
        return menuListVoList;
    }

    /**
     * 根据人员ID获取菜单权限列表
     *
     * @param userId 人员ID
     * @return
     */
    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {
        return menuMapper.getMenuListByUserId(userId);
    }
}