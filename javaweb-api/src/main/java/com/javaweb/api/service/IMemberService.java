

package com.javaweb.api.service;

import com.javaweb.api.dto.LoginDto;
import com.javaweb.api.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.common.utils.JsonResult;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-10-20
 */
public interface IMemberService extends IService<Member> {

    /**
     * 会员登录
     *
     * @param loginDto 参数
     * @return
     */
    JsonResult login(LoginDto loginDto);

}
