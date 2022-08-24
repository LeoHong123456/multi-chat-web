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
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.constant.AdConstant;
import com.javaweb.system.entity.Ad;
import com.javaweb.system.entity.AdSort;
import com.javaweb.system.mapper.AdMapper;
import com.javaweb.system.mapper.AdSortMapper;
import com.javaweb.system.query.AdQuery;
import com.javaweb.system.service.IAdService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.ad.AdListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 广告 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-03
 */
@Service
public class AdServiceImpl extends BaseServiceImpl<AdMapper, Ad> implements IAdService {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    private AdSortMapper adSortMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AdQuery adQuery = (AdQuery) query;
        // 查询条件
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        // 广告标题
        if (!StringUtils.isEmpty(adQuery.getTitle())) {
            queryWrapper.like("title", adQuery.getTitle());
        }
        // 广告格式：1图片 2文字 3视频 4推荐
        if (adQuery.getType() != null) {
            queryWrapper.eq("type", adQuery.getType());
        }
        // 状态：1在用 2停用
        if (adQuery.getStatus() != null) {
            queryWrapper.eq("status", adQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Ad> page = new Page<>(adQuery.getPage(), adQuery.getLimit());
        IPage<Ad> data = adMapper.selectPage(page, queryWrapper);
        List<Ad> adList = data.getRecords();
        List<AdListVo> adListVoList = new ArrayList<>();
        if (!adList.isEmpty()) {
            adList.forEach(item -> {
                AdListVo adListVo = new AdListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, adListVo);
                // 广告图片地址
                if (!StringUtils.isEmpty(adListVo.getCover())) {
                    adListVo.setCoverUrl(CommonUtils.getImageURL(adListVo.getCover()));
                }
                // 广告格式描述
                if (adListVo.getType() != null && adListVo.getType() > 0) {
                    adListVo.setTypeName(AdConstant.AD_TYPE_LIST.get(adListVo.getType()));
                }
                // 状态描述
                if (adListVo.getStatus() != null && adListVo.getStatus() > 0) {
                    adListVo.setStatusName(AdConstant.AD_STATUS_LIST.get(adListVo.getStatus()));
                }
                // 广告位
                AdSort adSort = adSortMapper.selectById(adListVo.getSortId());
                if (adSort != null) {
                    adListVo.setSortName(String.format("%s=>%s", adSort.getName(), adSort.getLocId()));
                }
                // 添加人名称
                if (adListVo.getCreateUser() > 0) {
                    adListVo.setCreateUserName(UserUtils.getName((adListVo.getCreateUser())));
                }
                // 更新人名称
                if (adListVo.getUpdateUser() > 0) {
                    adListVo.setUpdateUserName(UserUtils.getName((adListVo.getUpdateUser())));
                }
                adListVoList.add(adListVo);
            });
        }
        return JsonResult.success("操作成功", adListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Ad entity = (Ad) super.getInfo(id);
        // 广告图片解析
        if (!StringUtils.isEmpty(entity.getCover())) {
            entity.setCover(CommonUtils.getImageURL(entity.getCover()));
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
    public JsonResult edit(Ad entity) {
        // 广告图片
        if (entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
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
        Ad entity = this.getById(id);
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
    public JsonResult setStatus(Ad entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}