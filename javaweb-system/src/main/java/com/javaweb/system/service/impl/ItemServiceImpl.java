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
import com.javaweb.system.constant.ItemConstant;
import com.javaweb.system.entity.Item;
import com.javaweb.system.mapper.ItemMapper;
import com.javaweb.system.query.ItemQuery;
import com.javaweb.system.service.IItemService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.item.ItemListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 站点 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-04-20
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ItemQuery itemQuery = (ItemQuery) query;
        // 查询条件
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        // 站点名称
        if (!StringUtils.isEmpty(itemQuery.getName())) {
            queryWrapper.like("name", itemQuery.getName());
        }
        // 站点类型:1普通站点 2其他
        if (itemQuery.getType() != null) {
            queryWrapper.eq("type", itemQuery.getType());
        }
        // 状态：1在用 2停用
        if (itemQuery.getStatus() != null) {
            queryWrapper.eq("status", itemQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Item> page = new Page<>(itemQuery.getPage(), itemQuery.getLimit());
        IPage<Item> data = itemMapper.selectPage(page, queryWrapper);
        List<Item> itemList = data.getRecords();
        List<ItemListVo> itemListVoList = new ArrayList<>();
        if (!itemList.isEmpty()) {
            itemList.forEach(item -> {
                ItemListVo itemListVo = new ItemListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, itemListVo);
                // 站点类型描述
                if (itemListVo.getType() != null && itemListVo.getType() > 0) {
                    itemListVo.setTypeName(ItemConstant.ITEM_TYPE_LIST.get(itemListVo.getType()));
                }
                // 站点图片地址
                if (!StringUtils.isEmpty(itemListVo.getImage())) {
                    itemListVo.setImageUrl(CommonUtils.getImageURL(itemListVo.getImage()));
                }
                // 是否二级域名描述
                if (itemListVo.getIsDomain() != null && itemListVo.getIsDomain() > 0) {
                    itemListVo.setIsDomainName(ItemConstant.ITEM_ISDOMAIN_LIST.get(itemListVo.getIsDomain()));
                }
                // 状态描述
                if (itemListVo.getStatus() != null && itemListVo.getStatus() > 0) {
                    itemListVo.setStatusName(ItemConstant.ITEM_STATUS_LIST.get(itemListVo.getStatus()));
                }
                // 添加人名称
                if (itemListVo.getCreateUser() > 0) {
                    itemListVo.setCreateUserName(UserUtils.getName((itemListVo.getCreateUser())));
                }
                // 更新人名称
                if (itemListVo.getUpdateUser() > 0) {
                    itemListVo.setUpdateUserName(UserUtils.getName((itemListVo.getUpdateUser())));
                }
                itemListVoList.add(itemListVo);
            });
        }
        return JsonResult.success("操作成功", itemListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Item entity = (Item) super.getInfo(id);
        // 站点图片解析
        if (!StringUtils.isEmpty(entity.getImage())) {
            entity.setImage(CommonUtils.getImageURL(entity.getImage()));
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
    public JsonResult edit(Item entity) {
        // 站点图片
        if (entity.getImage().contains(CommonConfig.imageURL)) {
            entity.setImage(entity.getImage().replaceAll(CommonConfig.imageURL, ""));
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
        Item entity = this.getById(id);
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
    public JsonResult setStatus(Item entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}