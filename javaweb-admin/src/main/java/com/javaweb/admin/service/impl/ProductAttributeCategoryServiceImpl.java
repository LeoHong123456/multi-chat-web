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

package com.javaweb.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.admin.entity.ProductAttributeCategory;
import com.javaweb.admin.mapper.ProductAttributeCategoryMapper;
import com.javaweb.admin.query.ProductAttributeCategoryQuery;
import com.javaweb.admin.service.IProductAttributeCategoryService;
import com.javaweb.admin.vo.productattributecategory.ProductAttributeCategoryListVo;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品属性分类 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-06-08
 */
@Service
public class ProductAttributeCategoryServiceImpl extends BaseServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements IProductAttributeCategoryService {

    @Autowired
    private ProductAttributeCategoryMapper productAttributeCategoryMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ProductAttributeCategoryQuery productAttributeCategoryQuery = (ProductAttributeCategoryQuery) query;
        // 查询条件
        QueryWrapper<ProductAttributeCategory> queryWrapper = new QueryWrapper<>();
        // 分类名称
        if (!StringUtils.isEmpty(productAttributeCategoryQuery.getName())) {
            queryWrapper.like("name", productAttributeCategoryQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<ProductAttributeCategory> page = new Page<>(productAttributeCategoryQuery.getPage(), productAttributeCategoryQuery.getLimit());
        IPage<ProductAttributeCategory> data = productAttributeCategoryMapper.selectPage(page, queryWrapper);
        List<ProductAttributeCategory> productAttributeCategoryList = data.getRecords();
        List<ProductAttributeCategoryListVo> productAttributeCategoryListVoList = new ArrayList<>();
        if (!productAttributeCategoryList.isEmpty()) {
            productAttributeCategoryList.forEach(item -> {
                ProductAttributeCategoryListVo productAttributeCategoryListVo = new ProductAttributeCategoryListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, productAttributeCategoryListVo);
                // 分类图片地址
                if (!StringUtils.isEmpty(productAttributeCategoryListVo.getImage())) {
                    productAttributeCategoryListVo.setImageUrl(CommonUtils.getImageURL(productAttributeCategoryListVo.getImage()));
                }
                // 添加人名称
                if (productAttributeCategoryListVo.getCreateUser() != null && productAttributeCategoryListVo.getCreateUser() > 0) {
                    productAttributeCategoryListVo.setCreateUserName(UserUtils.getName((productAttributeCategoryListVo.getCreateUser())));
                }
                // 更新人名称
                if (productAttributeCategoryListVo.getUpdateUser() != null && productAttributeCategoryListVo.getUpdateUser() > 0) {
                    productAttributeCategoryListVo.setUpdateUserName(UserUtils.getName((productAttributeCategoryListVo.getUpdateUser())));
                }
                productAttributeCategoryListVoList.add(productAttributeCategoryListVo);
            });
        }
        return JsonResult.success("操作成功", productAttributeCategoryListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ProductAttributeCategory entity = (ProductAttributeCategory) super.getInfo(id);
        // 分类图片解析
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
    public JsonResult edit(ProductAttributeCategory entity) {
        // 分类图片
        if (entity.getImage().contains(CommonConfig.imageURL)) {
            entity.setImage(entity.getImage().replaceAll(CommonConfig.imageURL, ""));
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
        ProductAttributeCategory entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}