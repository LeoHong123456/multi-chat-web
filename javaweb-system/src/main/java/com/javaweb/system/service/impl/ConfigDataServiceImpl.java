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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.constant.ConfigDataConstant;
import com.javaweb.system.entity.ConfigData;
import com.javaweb.system.mapper.ConfigDataMapper;
import com.javaweb.system.query.ConfigDataQuery;
import com.javaweb.system.service.IConfigDataService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.configdata.ConfigDataListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 配置数据 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-03
 */
@Service
public class ConfigDataServiceImpl extends BaseServiceImpl<ConfigDataMapper, ConfigData> implements IConfigDataService {

    @Autowired
    private ConfigDataMapper configMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ConfigDataQuery configQuery = (ConfigDataQuery) query;
        // 查询条件
        QueryWrapper<ConfigData> queryWrapper = new QueryWrapper<>();
        // 分组ID
        queryWrapper.eq("config_id", configQuery.getConfigId());
        // 配置标题
        if (!StringUtils.isEmpty(configQuery.getTitle())) {
            queryWrapper.like("title", configQuery.getTitle());
        }
        // 配置类型：hidden=隐藏 readonly=只读文本 number=数字 text=单行文本 textarea=多行文本 array=数组 password=密码 radio=单选框 checkbox=复选框 select=下拉框 icon=字体图标 date=日期 datetime=时间 image=单张图片 images=多张图片 file=单个文件 files=多个文件 ueditor=富文本编辑器 json=JSON
        if (!StringUtils.isEmpty(configQuery.getType())) {
            queryWrapper.eq("type", configQuery.getType());
        }
        // 状态：1正常 2停用
        if (configQuery.getStatus() != null && configQuery.getStatus() > 0) {
            queryWrapper.eq("status", configQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询数据
        IPage<ConfigData> page = new Page<>(configQuery.getPage(), configQuery.getLimit());
        IPage<ConfigData> data = configMapper.selectPage(page, queryWrapper);
        List<ConfigData> configList = data.getRecords();
        List<ConfigDataListVo> configListVoList = new ArrayList<>();
        if (!configList.isEmpty()) {
            configList.forEach(item -> {
                ConfigDataListVo configListVo = new ConfigDataListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, configListVo);
                // 配置类型描述
                if (!StringUtils.isEmpty(configListVo.getType())) {
                    configListVo.setTypeName(ConfigDataConstant.CONFIG_DATA_TYPE_LIST.get(configListVo.getType()));
                }
                // 状态描述
                if (configListVo.getStatus() != null && configListVo.getStatus() > 0) {
                    configListVo.setStatusName(ConfigDataConstant.CONFIG_DATA_STATUS_LIST.get(configListVo.getStatus()));
                }
                // 添加人名称
                if (configListVo.getCreateUser() > 0) {
                    configListVo.setCreateUserName(UserUtils.getName((configListVo.getCreateUser())));
                }
                // 更新人名称
                if (configListVo.getUpdateUser() > 0) {
                    configListVo.setUpdateUserName(UserUtils.getName((configListVo.getUpdateUser())));
                }
                configListVoList.add(configListVo);
            });
        }
        return JsonResult.success("操作成功", configListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ConfigData entity = (ConfigData) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(ConfigData entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
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
        ConfigData entity = this.getById(id);
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
    public JsonResult setStatus(ConfigData entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 根据分组ID获取配置列表
     *
     * @param configId 分组ID
     * @return
     */
    @Override
    public List<ConfigData> getConfigListByGroupId(Integer configId) {
        // 查询条件
        QueryWrapper<ConfigData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_id", configId);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<ConfigData> configList = configMapper.selectList(queryWrapper);
        if (!configList.isEmpty()) {
            configList.forEach(item -> {
                if (item.getType().equals("image")) {
                    // 单图处理
                    if (!StringUtils.isEmpty(item.getValue())) {
                        item.setValue(CommonUtils.getImageURL(item.getValue()));
                    }
                } else if (item.getType().equals("images")) {
                    // 多图处理
                }
            });
        }
        return configList;
    }
}