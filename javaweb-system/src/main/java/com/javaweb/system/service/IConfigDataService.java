

package com.javaweb.system.service;

import com.javaweb.system.entity.ConfigData;
import com.javaweb.common.common.IBaseService;

import java.util.List;

/**
 * <p>
 * 配置数据 服务类
 * </p>
 *
 * @author admin
 * @since 2020-05-03
 */
public interface IConfigDataService extends IBaseService<ConfigData> {

    /**
     * 根据配置ID获取配置列表
     *
     * @param configId 配置ID
     * @return
     */
    List<ConfigData> getConfigListByGroupId(Integer configId);

}