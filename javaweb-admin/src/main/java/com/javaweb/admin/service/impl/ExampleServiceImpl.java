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

package com.javaweb.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.admin.vo.example.ExampleListVo;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.admin.constant.ExampleConstant;
import com.javaweb.admin.entity.Example;
import com.javaweb.admin.mapper.ExampleMapper;
import com.javaweb.admin.query.ExampleQuery;
import com.javaweb.admin.service.IExampleService;
import com.javaweb.common.common.BaseServiceImpl;
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
 * 演示案例一 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-25
 */
@Service
public class ExampleServiceImpl extends BaseServiceImpl<ExampleMapper, Example> implements IExampleService {

    @Autowired
    private ExampleMapper exampleMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ExampleQuery exampleQuery = (ExampleQuery) query;
        // 查询条件
        QueryWrapper<Example> queryWrapper = new QueryWrapper<>();
        // 案例名称
        if (!StringUtils.isEmpty(exampleQuery.getName())) {
            queryWrapper.like("name", exampleQuery.getName());
        }
        // 类型：1京东 2淘宝 3拼多多 4唯品会
        if (exampleQuery.getType() != null && exampleQuery.getType() > 0) {
            queryWrapper.eq("type", exampleQuery.getType());
        }
        // 是否VIP：1是 2否
        if (exampleQuery.getIsVip() != null && exampleQuery.getIsVip() > 0) {
            queryWrapper.eq("is_vip", exampleQuery.getIsVip());
        }
        // 状态：1正常 2停用
        if (exampleQuery.getStatus() != null && exampleQuery.getStatus() > 0) {
            queryWrapper.eq("status", exampleQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Example> page = new Page<>(exampleQuery.getPage(), exampleQuery.getLimit());
        IPage<Example> data = exampleMapper.selectPage(page, queryWrapper);
        List<Example> exampleList = data.getRecords();
        List<ExampleListVo> exampleListVoList = new ArrayList<>();
        if (!exampleList.isEmpty()) {
            exampleList.forEach(item -> {
                ExampleListVo exampleListVo = new ExampleListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, exampleListVo);
                // 头像地址
                if (!StringUtils.isEmpty(exampleListVo.getAvatar())) {
                    exampleListVo.setAvatarUrl(CommonUtils.getImageURL(exampleListVo.getAvatar()));
                }
                // 类型描述
                if (exampleListVo.getType() != null && exampleListVo.getType() > 0) {
                    exampleListVo.setTypeName(ExampleConstant.EXAMPLE_TYPE_LIST.get(exampleListVo.getType()));
                }
                // 是否VIP描述
                if (exampleListVo.getIsVip() != null && exampleListVo.getIsVip() > 0) {
                    exampleListVo.setIsVipName(ExampleConstant.EXAMPLE_ISVIP_LIST.get(exampleListVo.getIsVip()));
                }
                // 状态描述
                if (exampleListVo.getStatus() != null && exampleListVo.getStatus() > 0) {
                    exampleListVo.setStatusName(ExampleConstant.EXAMPLE_STATUS_LIST.get(exampleListVo.getStatus()));
                }
                // 添加人名称
                if (exampleListVo.getCreateUser() != null && exampleListVo.getCreateUser() > 0) {
                    exampleListVo.setCreateUserName(UserUtils.getName((exampleListVo.getCreateUser())));
                }
                // 更新人名称
                if (exampleListVo.getUpdateUser() != null && exampleListVo.getUpdateUser() > 0) {
                    exampleListVo.setUpdateUserName(UserUtils.getName((exampleListVo.getUpdateUser())));
                }
                exampleListVoList.add(exampleListVo);
            });
        }
        return JsonResult.success("操作成功", exampleListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Example entity = (Example) super.getInfo(id);
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
    public JsonResult edit(Example entity) {
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
        Example entity = this.getById(id);
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
    public JsonResult setStatus(Example entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}