

package com.javaweb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.constant.DeptConstant;
import com.javaweb.system.entity.Dept;
import com.javaweb.system.mapper.DeptMapper;
import com.javaweb.system.query.DeptQuery;
import com.javaweb.system.service.IDeptService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.dept.DeptListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-28
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DeptQuery depQuery = (DeptQuery) query;
        // 查询条件
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        // 部门名称
        if (!StringUtils.isEmpty(depQuery.getName())) {
            queryWrapper.like("name", depQuery.getName());
        }
        // 类型：1公司 2部门
        if (depQuery.getType() != null) {
            queryWrapper.eq("type", depQuery.getType());
        }
        // 是否有子级：1是 2否
        if (depQuery.getHasChild() != null) {
            queryWrapper.eq("has_child", depQuery.getHasChild());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("id");

        // 查询数据
        IPage<Dept> page = new Page<>(depQuery.getPage(), depQuery.getLimit());
        IPage<Dept> data = deptMapper.selectPage(page, queryWrapper);
        List<Dept> deptList = data.getRecords();
        List<DeptListVo> deptListVoList = new ArrayList<>();
        if (!deptList.isEmpty()) {
            deptList.forEach(item -> {
                DeptListVo deptListVo = new DeptListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, deptListVo);
                // 类型描述
                if (deptListVo.getType() != null && deptListVo.getType() > 0) {
                    deptListVo.setTypeName(DeptConstant.DEPT_TYPE_LIST.get(deptListVo.getType()));
                }
                // 是否有子级描述
                if (deptListVo.getHasChild() != null && deptListVo.getHasChild() > 0) {
                    deptListVo.setHasChildName(DeptConstant.DEPT_HASCHILD_LIST.get(deptListVo.getHasChild()));
                }
                // 添加人名称
                if (deptListVo.getCreateUser() > 0) {
                    deptListVo.setCreateUserName(UserUtils.getName((deptListVo.getCreateUser())));
                }
                // 更新人名称
                if (deptListVo.getUpdateUser() > 0) {
                    deptListVo.setUpdateUserName(UserUtils.getName((deptListVo.getUpdateUser())));
                }
                deptListVoList.add(deptListVo);
            });
        }
        return JsonResult.success("操作成功", deptListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Dept entity = (Dept) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Dept entity) {
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
        Dept entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 根据部门ID获取部门名称
     *
     * @param deptId    部门ID
     * @param delimiter 拼接字符
     * @return
     */
    @Override
    public String getDeptNameById(Integer deptId, String delimiter) {
        List<String> nameList = new ArrayList<>();
        while (deptId > 0) {
            Dept depInfo = deptMapper.selectById(deptId);
            if (depInfo != null) {
                nameList.add(depInfo.getName());
                deptId = depInfo.getPid();
            } else {
                deptId = 1;
            }
        }
        // 使用集合工具实现数组翻转
        Collections.reverse(nameList);
        return org.apache.commons.lang3.StringUtils.join(nameList.toArray(), delimiter);
    }

}
