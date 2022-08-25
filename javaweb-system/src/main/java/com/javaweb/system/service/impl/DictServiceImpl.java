

package com.javaweb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.entity.Dict;
import com.javaweb.system.mapper.DictMapper;
import com.javaweb.system.query.DictQuery;
import com.javaweb.system.service.IDictService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.dict.DictListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DictQuery dictQuery = (DictQuery) query;
        // 查询条件
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        // 字典名称
        if (!StringUtils.isEmpty(dictQuery.getName())) {
            queryWrapper.like("name", dictQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询数据
        IPage<Dict> page = new Page<>(dictQuery.getPage(), dictQuery.getLimit());
        IPage<Dict> data = dictMapper.selectPage(page, queryWrapper);
        List<Dict> dictList = data.getRecords();
        List<DictListVo> dictListVoList = new ArrayList<>();
        if (!dictList.isEmpty()) {
            dictList.forEach(item -> {
                DictListVo dictListVo = new DictListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, dictListVo);
                // 添加人名称
                if (dictListVo.getCreateUser() > 0) {
                    dictListVo.setCreateUserName(UserUtils.getName((dictListVo.getCreateUser())));
                }
                // 更新人名称
                if (dictListVo.getUpdateUser() > 0) {
                    dictListVo.setUpdateUserName(UserUtils.getName((dictListVo.getUpdateUser())));
                }
                dictListVoList.add(dictListVo);
            });
        }
        return JsonResult.success("操作成功", dictListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Dict entity = (Dict) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Dict entity) {
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
        Dict entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}