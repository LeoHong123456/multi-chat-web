

package com.javaweb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.constant.DictDataConstant;
import com.javaweb.system.entity.DictData;
import com.javaweb.system.mapper.DictDataMapper;
import com.javaweb.system.query.DictDataQuery;
import com.javaweb.system.service.IDictDataService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.dictdata.DictDataListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    @Autowired
    private DictDataMapper dicMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DictDataQuery dicQuery = (DictDataQuery) query;
        // 查询条件
        QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
        // 字典名称
        if (!StringUtils.isEmpty(dicQuery.getTitle())) {
            queryWrapper.like("title", dicQuery.getTitle());
        }
        // 状态：1在用 2停用
        if (dicQuery.getStatus() != null) {
            queryWrapper.eq("status", dicQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<DictData> page = new Page<>(dicQuery.getPage(), dicQuery.getLimit());
        IPage<DictData> data = dicMapper.selectPage(page, queryWrapper);
        List<DictData> dicList = data.getRecords();
        List<DictDataListVo> dicListVoList = new ArrayList<>();
        if (!dicList.isEmpty()) {
            dicList.forEach(item -> {
                DictDataListVo dicListVo = new DictDataListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, dicListVo);
                // 状态描述
                if (dicListVo.getStatus() != null && dicListVo.getStatus() > 0) {
                    dicListVo.setStatusName(DictDataConstant.DICT_DATA_STATUS_LIST.get(dicListVo.getStatus()));
                }
                // 添加人名称
                if (dicListVo.getCreateUser() > 0) {
                    dicListVo.setCreateUserName(UserUtils.getName((dicListVo.getCreateUser())));
                }
                // 更新人名称
                if (dicListVo.getUpdateUser() > 0) {
                    dicListVo.setUpdateUserName(UserUtils.getName((dicListVo.getUpdateUser())));
                }
                dicListVoList.add(dicListVo);
            });
        }
        return JsonResult.success("操作成功", dicListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        DictData entity = (DictData) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(DictData entity) {
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
        DictData entity = this.getById(id);
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
    public JsonResult setStatus(DictData entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}