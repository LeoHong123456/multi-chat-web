

package com.javaweb.system.service;

import com.javaweb.system.entity.Menu;
import com.javaweb.common.common.IBaseService;
import com.javaweb.system.vo.menu.MenuListVo;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-07
 */
public interface IMenuService extends IBaseService<Menu> {

    /**
     * 根据用户ID获取菜单权限
     *
     * @param userId 用户ID
     * @return
     */
    List<Menu> getMenuListByUserId(Integer userId);

    /**
     * 获取导航菜单
     *
     * @param userId 人员ID
     * @return
     */
    List<MenuListVo> getNavbarMenu(Integer userId);
}