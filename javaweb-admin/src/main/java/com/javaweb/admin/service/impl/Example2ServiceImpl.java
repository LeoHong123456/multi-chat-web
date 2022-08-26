

package com.javaweb.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.admin.vo.example2.Example2ListVo;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.admin.constant.Example2Constant;
import com.javaweb.admin.entity.Example2;
import com.javaweb.admin.mapper.Example2Mapper;
import com.javaweb.admin.query.Example2Query;
import com.javaweb.admin.service.IExample2Service;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 演示案例二 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-10-19
 */
@Service
public class Example2ServiceImpl extends BaseServiceImpl<Example2Mapper, Example2> implements IExample2Service {

    @Autowired
    private Example2Mapper example2Mapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        Example2Query example2Query = (Example2Query) query;
        // 查询条件
        QueryWrapper<Example2> queryWrapper = new QueryWrapper<>();
        // 案例名称
        if (!StringUtils.isEmpty(example2Query.getName())) {
            queryWrapper.like("name", example2Query.getName());
        }
        // 类型：1京东 2淘宝 3拼多多 4唯品会
        if (example2Query.getType() != null && example2Query.getType() > 0) {
            queryWrapper.eq("type", example2Query.getType());
        }
        // 状态：1正常 2停用
        if (example2Query.getStatus() != null && example2Query.getStatus() > 0) {
            queryWrapper.eq("status", example2Query.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Example2> page = new Page<>(example2Query.getPage(), example2Query.getLimit());
        IPage<Example2> data = example2Mapper.selectPage(page, queryWrapper);
        List<Example2> example2List = data.getRecords();
        List<Example2ListVo> example2ListVoList = new ArrayList<>();
        if (!example2List.isEmpty()) {
            example2List.forEach(item -> {
                Example2ListVo example2ListVo = new Example2ListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, example2ListVo);
                // 头像地址
                if (!StringUtils.isEmpty(example2ListVo.getAvatar())) {
                    example2ListVo.setAvatarUrl(CommonUtils.getImageURL(example2ListVo.getAvatar()));
                }
                // 类型描述
                if (example2ListVo.getType() != null && example2ListVo.getType() > 0) {
                    example2ListVo.setTypeName(Example2Constant.EXAMPLE2_TYPE_LIST.get(example2ListVo.getType()));
                }
                // 状态描述
                if (example2ListVo.getStatus() != null && example2ListVo.getStatus() > 0) {
                    example2ListVo.setStatusName(Example2Constant.EXAMPLE2_STATUS_LIST.get(example2ListVo.getStatus()));
                }
                // 添加人名称
                if (example2ListVo.getCreateUser() != null && example2ListVo.getCreateUser() > 0) {
                    example2ListVo.setCreateUserName(UserUtils.getName((example2ListVo.getCreateUser())));
                }
                // 更新人名称
                if (example2ListVo.getUpdateUser() != null && example2ListVo.getUpdateUser() > 0) {
                    example2ListVo.setUpdateUserName(UserUtils.getName((example2ListVo.getUpdateUser())));
                }
                example2ListVoList.add(example2ListVo);
            });
        }
        return JsonResult.success("操作成功", example2ListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Example2 entity = (Example2) super.getInfo(id);
        // 头像解析
        if (!StringUtils.isEmpty(entity.getAvatar())) {
            entity.setAvatar(CommonUtils.getImageURL(entity.getAvatar()));
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
    public JsonResult edit(Example2 entity) {
        // 头像
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
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
        Example2 entity = this.getById(id);
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
    public JsonResult setStatus(Example2 entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}