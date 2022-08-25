

package com.javaweb.system.service;

import com.javaweb.system.entity.Role;
import com.javaweb.common.common.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 根据人员ID获取角色列表
     *
     * @param userId 人员ID
     * @return
     */
    List<Role> getRoleListByUserId(Integer userId);

}