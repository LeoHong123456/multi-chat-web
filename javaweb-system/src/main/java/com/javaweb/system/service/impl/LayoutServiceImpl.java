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
import com.javaweb.system.constant.LayoutConstant;
import com.javaweb.system.entity.Item;
import com.javaweb.system.entity.Layout;
import com.javaweb.system.entity.LayoutDesc;
import com.javaweb.system.mapper.ItemMapper;
import com.javaweb.system.mapper.LayoutMapper;
import com.javaweb.system.query.LayoutQuery;
import com.javaweb.system.service.ILayoutDescService;
import com.javaweb.system.service.ILayoutService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.layout.LayoutListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 布局 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-03
 */
@Service
public class LayoutServiceImpl extends BaseServiceImpl<LayoutMapper, Layout> implements ILayoutService {

    @Autowired
    private LayoutMapper layoutMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ILayoutDescService layoutDescService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LayoutQuery layoutQuery = (LayoutQuery) query;
        // 查询条件
        QueryWrapper<Layout> queryWrapper = new QueryWrapper<>();
        // 类型：1新闻资讯 2其他
        if (layoutQuery.getType() != null && layoutQuery.getType() > 0) {
            queryWrapper.eq("type", layoutQuery.getType());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Layout> page = new Page<>(layoutQuery.getPage(), layoutQuery.getLimit());
        IPage<Layout> data = layoutMapper.selectPage(page, queryWrapper);
        List<Layout> layoutList = data.getRecords();
        List<LayoutListVo> layoutListVoList = new ArrayList<>();
        if (!layoutList.isEmpty()) {
            layoutList.forEach(item -> {
                LayoutListVo layoutListVo = new LayoutListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, layoutListVo);
                // 类型描述
                if (layoutListVo.getType() != null && layoutListVo.getType() > 0) {
                    layoutListVo.setTypeName(LayoutConstant.LAYOUT_TYPE_LIST.get(layoutListVo.getType()));
                }
                // 图片路径地址
                if (!StringUtils.isEmpty(layoutListVo.getImage())) {
                    layoutListVo.setImageUrl(CommonUtils.getImageURL(layoutListVo.getImage()));
                }
                // 页面编号
                if (layoutListVo.getItemId() != null && layoutListVo.getItemId() > 0) {
                    Item itemInfo = itemMapper.selectById(layoutListVo.getItemId());
                    if (itemInfo != null) {
                        layoutListVo.setItemName(itemInfo.getName());
                    }
                }
                // 获取页面位置描述
                LayoutDesc layoutDescInfo = layoutDescService.getLocDescById(layoutListVo.getItemId(), layoutListVo.getLocId());
                if (layoutDescInfo != null) {
                    layoutListVo.setLocDesc(String.format("%s=>%s", layoutDescInfo.getLocDesc(), layoutDescInfo.getLocId()));
                }

                // 添加人名称
                if (layoutListVo.getCreateUser() > 0) {
                    layoutListVo.setCreateUserName(UserUtils.getName((layoutListVo.getCreateUser())));
                }
                // 更新人名称
                if (layoutListVo.getUpdateUser() > 0) {
                    layoutListVo.setUpdateUserName(UserUtils.getName((layoutListVo.getUpdateUser())));
                }
                layoutListVoList.add(layoutListVo);
            });
        }
        return JsonResult.success("操作成功", layoutListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Layout entity = (Layout) super.getInfo(id);
        // 图片路径解析
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
    public JsonResult edit(Layout entity) {
        // 图片路径
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
        Layout entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}