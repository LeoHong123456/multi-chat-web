

package com.javaweb.system.service;

import com.javaweb.common.utils.JsonResult;

import java.util.Map;


/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-03
 */
public interface IConfigWebService {

    /**
     * 编辑配置信息
     *
     * @param map 参数
     * @return
     */
    JsonResult configEdit(Map<String, Object> map);

}