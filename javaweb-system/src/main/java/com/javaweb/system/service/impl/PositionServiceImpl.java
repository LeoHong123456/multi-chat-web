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
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.constant.PositionConstant;
import com.javaweb.system.entity.Position;
import com.javaweb.system.mapper.PositionMapper;
import com.javaweb.system.query.PositionQuery;
import com.javaweb.system.service.IPositionService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.position.PositionListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 岗位 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-04-20
 */
@Service
public class PositionServiceImpl extends BaseServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        PositionQuery positionQuery = (PositionQuery) query;
        // 查询条件
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        // 岗位名称
        if (!StringUtils.isEmpty(positionQuery.getName())) {
            queryWrapper.like("name", positionQuery.getName());
        }
        // 状态：1正常 2停用
        if (positionQuery.getStatus() != null) {
            queryWrapper.eq("status", positionQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询数据
        IPage<Position> page = new Page<>(positionQuery.getPage(), positionQuery.getLimit());
        IPage<Position> data = positionMapper.selectPage(page, queryWrapper);
        List<Position> positionList = data.getRecords();
        List<PositionListVo> positionListVoList = new ArrayList<>();
        if (!positionList.isEmpty()) {
            positionList.forEach(item -> {
                PositionListVo positionListVo = new PositionListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, positionListVo);
                // 状态描述
                if (positionListVo.getStatus() != null && positionListVo.getStatus() > 0) {
                    positionListVo.setStatusName(PositionConstant.POSITION_STATUS_LIST.get(positionListVo.getStatus()));
                }
                // 添加人名称
                if (positionListVo.getCreateUser() > 0) {
                    positionListVo.setCreateUserName(UserUtils.getName((positionListVo.getCreateUser())));
                }
                // 更新人名称
                if (positionListVo.getUpdateUser() > 0) {
                    positionListVo.setUpdateUserName(UserUtils.getName((positionListVo.getUpdateUser())));
                }
                positionListVoList.add(positionListVo);
            });
        }
        return JsonResult.success("操作成功", positionListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Position entity = (Position) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Position entity) {
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
        Position entity = this.getById(id);
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
    public JsonResult setStatus(Position entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}