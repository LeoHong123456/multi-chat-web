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
import com.javaweb.system.constant.AdSortConstant;
import com.javaweb.system.entity.AdSort;
import com.javaweb.system.entity.Item;
import com.javaweb.system.mapper.AdSortMapper;
import com.javaweb.system.mapper.ItemMapper;
import com.javaweb.system.query.AdSortQuery;
import com.javaweb.system.service.IAdSortService;
import com.javaweb.system.service.IItemCateService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.adsort.AdSortListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 广告位描述 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-01
 */
@Service
public class AdSortServiceImpl extends BaseServiceImpl<AdSortMapper, AdSort> implements IAdSortService {

    @Autowired
    private AdSortMapper adSortMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AdSortQuery adSortQuery = (AdSortQuery) query;
        // 查询条件
        QueryWrapper<AdSort> queryWrapper = new QueryWrapper<>();
        // 广告位名称
        if (!StringUtils.isEmpty(adSortQuery.getName())) {
            queryWrapper.like("name", adSortQuery.getName());
        }
        // 站点类型：1PC网站 2WAP手机站 3微信小程序 4APP移动端
        if (adSortQuery.getPlatform() != null) {
            queryWrapper.eq("platform", adSortQuery.getPlatform());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<AdSort> page = new Page<>(adSortQuery.getPage(), adSortQuery.getLimit());
        IPage<AdSort> data = adSortMapper.selectPage(page, queryWrapper);
        List<AdSort> adSortList = data.getRecords();
        List<AdSortListVo> adSortListVoList = new ArrayList<>();
        if (!adSortList.isEmpty()) {
            adSortList.forEach(item -> {
                AdSortListVo adSortListVo = new AdSortListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, adSortListVo);
                // 站点名称
                if (adSortListVo.getItemId() != null && adSortListVo.getItemId() > 0) {
                    Item itemInfo = itemMapper.selectById(adSortListVo.getItemId());
                    if (itemInfo != null) {
                        adSortListVo.setItemName(itemInfo.getName());
                    }
                }
                // 栏目名称
                if (adSortListVo.getCateId() != null && adSortListVo.getCateId() > 0) {
                    String cateName = itemCateService.getCateNameByCateId(adSortListVo.getCateId(), ">>");
                    adSortListVo.setCateName(cateName);
                }
                // 站点类型描述
                if (adSortListVo.getPlatform() != null && adSortListVo.getPlatform() > 0) {
                    adSortListVo.setPlatformName(AdSortConstant.ADSORT_PLATFORM_LIST.get(adSortListVo.getPlatform()));
                }
                // 添加人名称
                if (adSortListVo.getCreateUser() > 0) {
                    adSortListVo.setCreateUserName(UserUtils.getName((adSortListVo.getCreateUser())));
                }
                // 更新人名称
                if (adSortListVo.getUpdateUser() > 0) {
                    adSortListVo.setUpdateUserName(UserUtils.getName((adSortListVo.getUpdateUser())));
                }
                adSortListVoList.add(adSortListVo);
            });
        }
        return JsonResult.success("操作成功", adSortListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        AdSort entity = (AdSort) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(AdSort entity) {
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
        AdSort entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}