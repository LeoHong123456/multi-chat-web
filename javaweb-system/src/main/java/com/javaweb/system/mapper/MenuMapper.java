

package com.javaweb.system.mapper;

import com.javaweb.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.system.vo.menu.MenuListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-05-07
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据人员ID获取菜单列表
     *
     * @param userId 人员ID
     * @return
     */
    List<Menu> getMenuListByUserId(@Param("userId") Integer userId);

    /**
     * 获取权限菜单
     *
     * @param userId 人员ID
     * @param pid    上级ID
     * @return
     */
    List<MenuListVo> getPermissionMenuList(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
