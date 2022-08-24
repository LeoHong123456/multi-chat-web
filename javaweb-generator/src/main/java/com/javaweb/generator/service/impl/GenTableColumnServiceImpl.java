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

package com.javaweb.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.generator.constant.GenTableColumnConstant;
import com.javaweb.generator.entity.GenTableColumn;
import com.javaweb.generator.mapper.GenTableColumnMapper;
import com.javaweb.generator.query.GenTableColumnQuery;
import com.javaweb.generator.service.IGenTableColumnService;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.generator.vo.GenTableColumnListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代码生成列 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-10
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        GenTableColumnQuery genTableColumnQuery = (GenTableColumnQuery) query;
        // 查询条件
        QueryWrapper<GenTableColumn> queryWrapper = new QueryWrapper<>();
        // 是否主键：1是 2否
        if (genTableColumnQuery.getIsPk() != null && genTableColumnQuery.getIsPk() > 0) {
            queryWrapper.eq("is_pk", genTableColumnQuery.getIsPk());
        }
        // 是否自增：1是 2否
        if (genTableColumnQuery.getIsIncrement() != null && genTableColumnQuery.getIsIncrement() > 0) {
            queryWrapper.eq("is_increment", genTableColumnQuery.getIsIncrement());
        }
        // 是否必填：1是 2否
        if (genTableColumnQuery.getIsRequired() != null && genTableColumnQuery.getIsRequired() > 0) {
            queryWrapper.eq("is_required", genTableColumnQuery.getIsRequired());
        }
        // 是否为插入字段：1是 2否
        if (genTableColumnQuery.getIsInsert() != null && genTableColumnQuery.getIsInsert() > 0) {
            queryWrapper.eq("is_insert", genTableColumnQuery.getIsInsert());
        }
        // 是否编辑字段：1是 2否
        if (genTableColumnQuery.getIsEdit() != null && genTableColumnQuery.getIsEdit() > 0) {
            queryWrapper.eq("is_edit", genTableColumnQuery.getIsEdit());
        }
        // 是否列表字段：1是 2否
        if (genTableColumnQuery.getIsList() != null && genTableColumnQuery.getIsList() > 0) {
            queryWrapper.eq("is_list", genTableColumnQuery.getIsList());
        }
        // 是否查询字段：1是 2否
        if (genTableColumnQuery.getIsQuery() != null && genTableColumnQuery.getIsQuery() > 0) {
            queryWrapper.eq("is_query", genTableColumnQuery.getIsQuery());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<GenTableColumn> page = new Page<>(genTableColumnQuery.getPage(), genTableColumnQuery.getLimit());
        IPage<GenTableColumn> data = genTableColumnMapper.selectPage(page, queryWrapper);
        List<GenTableColumn> genTableColumnList = data.getRecords();
        List<GenTableColumnListVo> genTableColumnListVoList = new ArrayList<>();
        if (!genTableColumnList.isEmpty()) {
            genTableColumnList.forEach(item -> {
                GenTableColumnListVo genTableColumnListVo = new GenTableColumnListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, genTableColumnListVo);
                // 是否主键描述
                if (genTableColumnListVo.getIsPk() != null && genTableColumnListVo.getIsPk() > 0) {
                    genTableColumnListVo.setIsPkName(GenTableColumnConstant.GENTABLECOLUMN_ISPK_LIST.get(genTableColumnListVo.getIsPk()));
                }
                // 是否自增描述
                if (genTableColumnListVo.getIsIncrement() != null && genTableColumnListVo.getIsIncrement() > 0) {
                    genTableColumnListVo.setIsIncrementName(GenTableColumnConstant.GENTABLECOLUMN_ISINCREMENT_LIST.get(genTableColumnListVo.getIsIncrement()));
                }
                // 是否必填描述
                if (genTableColumnListVo.getIsRequired() != null && genTableColumnListVo.getIsRequired() > 0) {
                    genTableColumnListVo.setIsRequiredName(GenTableColumnConstant.GENTABLECOLUMN_ISREQUIRED_LIST.get(genTableColumnListVo.getIsRequired()));
                }
                // 是否为插入字段描述
                if (genTableColumnListVo.getIsInsert() != null && genTableColumnListVo.getIsInsert() > 0) {
                    genTableColumnListVo.setIsInsertName(GenTableColumnConstant.GENTABLECOLUMN_ISINSERT_LIST.get(genTableColumnListVo.getIsInsert()));
                }
                // 是否编辑字段描述
                if (genTableColumnListVo.getIsEdit() != null && genTableColumnListVo.getIsEdit() > 0) {
                    genTableColumnListVo.setIsEditName(GenTableColumnConstant.GENTABLECOLUMN_ISEDIT_LIST.get(genTableColumnListVo.getIsEdit()));
                }
                // 是否列表字段描述
                if (genTableColumnListVo.getIsList() != null && genTableColumnListVo.getIsList() > 0) {
                    genTableColumnListVo.setIsListName(GenTableColumnConstant.GENTABLECOLUMN_ISLIST_LIST.get(genTableColumnListVo.getIsList()));
                }
                // 是否查询字段描述
                if (genTableColumnListVo.getIsQuery() != null && genTableColumnListVo.getIsQuery() > 0) {
                    genTableColumnListVo.setIsQueryName(GenTableColumnConstant.GENTABLECOLUMN_ISQUERY_LIST.get(genTableColumnListVo.getIsQuery()));
                }
                // 添加人名称
                if (genTableColumnListVo.getCreateUser() != null && genTableColumnListVo.getCreateUser() > 0) {
                    genTableColumnListVo.setCreateUserName(UserUtils.getName((genTableColumnListVo.getCreateUser())));
                }
                // 更新人名称
                if (genTableColumnListVo.getUpdateUser() != null && genTableColumnListVo.getUpdateUser() > 0) {
                    genTableColumnListVo.setUpdateUserName(UserUtils.getName((genTableColumnListVo.getUpdateUser())));
                }
                genTableColumnListVoList.add(genTableColumnListVo);
            });
        }
        return JsonResult.success("操作成功", genTableColumnListVoList);
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        GenTableColumn entity = (GenTableColumn) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(GenTableColumn entity) {
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
        GenTableColumn entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}