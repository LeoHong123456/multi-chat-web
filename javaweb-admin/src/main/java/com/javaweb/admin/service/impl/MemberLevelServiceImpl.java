

package com.javaweb.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.admin.entity.MemberLevel;
import com.javaweb.admin.mapper.MemberLevelMapper;
import com.javaweb.admin.query.MemberLevelQuery;
import com.javaweb.admin.service.IMemberLevelService;
import com.javaweb.admin.vo.memberlevel.MemberLevelListVo;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
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
 * 会员级别表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-28
 */
@Service
public class MemberLevelServiceImpl extends BaseServiceImpl<MemberLevelMapper, MemberLevel> implements IMemberLevelService {

    @Autowired
    private MemberLevelMapper memberLevelMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MemberLevelQuery memberLevelQuery = (MemberLevelQuery) query;
        // 查询条件
        QueryWrapper<MemberLevel> queryWrapper = new QueryWrapper<>();
        // 级别名称
        if (!StringUtils.isEmpty(memberLevelQuery.getName())) {
            queryWrapper.like("name", memberLevelQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<MemberLevel> page = new Page<>(memberLevelQuery.getPage(), memberLevelQuery.getLimit());
        IPage<MemberLevel> data = memberLevelMapper.selectPage(page, queryWrapper);
        List<MemberLevel> userLevelList = data.getRecords();
        List<MemberLevelListVo> memberLevelListVoList = new ArrayList<>();
        if (!userLevelList.isEmpty()) {
            userLevelList.forEach(item -> {
                MemberLevelListVo memberLevelListVo = new MemberLevelListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, memberLevelListVo);
                // 添加人名称
                if (memberLevelListVo.getCreateUser() > 0) {
                    memberLevelListVo.setCreateUserName(UserUtils.getName((memberLevelListVo.getCreateUser())));
                }
                // 更新人名称
                if (memberLevelListVo.getUpdateUser() > 0) {
                    memberLevelListVo.setUpdateUserName(UserUtils.getName((memberLevelListVo.getUpdateUser())));
                }
                memberLevelListVoList.add(memberLevelListVo);
            });
        }
        return JsonResult.success("操作成功", memberLevelListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        MemberLevel entity = (MemberLevel) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(MemberLevel entity) {
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
        MemberLevel entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}
