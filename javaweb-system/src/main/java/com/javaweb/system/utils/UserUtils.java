

package com.javaweb.system.utils;

import com.javaweb.common.utils.SpringUtils;
import com.javaweb.system.entity.User;
import com.javaweb.system.mapper.UserMapper;

public class UserUtils {

    /**
     * 根据ID获取人员名称
     *
     * @param id 人员ID
     * @return
     */
    public static String getName(Integer id) {
        UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
        User user = userMapper.selectById(id);
        return user.getRealname();
    }
}
