// +----------------------------------------------------------------------
// | JavaWeb_Layui混编版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站:
// +----------------------------------------------------------------------
// | 作者: admin 
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
import com.javaweb.admin.constant.ProductAttributeConstant;
import com.javaweb.admin.entity.ProductAttribute;
import com.javaweb.admin.entity.ProductAttributeCategory;
import com.javaweb.admin.mapper.ProductAttributeCategoryMapper;
import com.javaweb.admin.mapper.ProductAttributeMapper;
import com.javaweb.admin.query.ProductAttributeQuery;
import com.javaweb.admin.service.IProductAttributeService;
import com.javaweb.admin.vo.productattribute.ProductAttributeListVo;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
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
 * 商品属性 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-06-08
 */
@Service
public class ProductAttributeServiceImpl extends BaseServiceImpl<ProductAttributeMapper, ProductAttribute> implements IProductAttributeService {

    @Autowired
    private ProductAttributeMapper productAttributeMapper;
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
        ProductAttributeQuery productAttributeQuery = (ProductAttributeQuery) query;
        // 查询条件
        QueryWrapper<ProductAttribute> queryWrapper = new QueryWrapper<>();
        // 属性名称
        if (!StringUtils.isEmpty(productAttributeQuery.getName())) {
            queryWrapper.like("name", productAttributeQuery.getName());
        }
        // 属性的类型：1规格 2属性
        if (productAttributeQuery.getType() != null && productAttributeQuery.getType() > 0) {
            queryWrapper.eq("type", productAttributeQuery.getType());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<ProductAttribute> page = new Page<>(productAttributeQuery.getPage(), productAttributeQuery.getLimit());
        IPage<ProductAttribute> data = productAttributeMapper.selectPage(page, queryWrapper);
        List<ProductAttribute> productAttributeList = data.getRecords();
        List<ProductAttributeListVo> productAttributeListVoList = new ArrayList<>();
        if (!productAttributeList.isEmpty()) {
            productAttributeList.forEach(item -> {
                ProductAttributeListVo productAttributeListVo = new ProductAttributeListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, productAttributeListVo);
                // 属性的类型描述
                if (productAttributeListVo.getType() != null && productAttributeListVo.getType() > 0) {
                    productAttributeListVo.setTypeName(ProductAttributeConstant.PRODUCTATTRIBUTE_TYPE_LIST.get(productAttributeListVo.getType()));
                }
                // 属性所属类型
                if (productAttributeListVo.getProductAttributeCategoryId() != null && productAttributeListVo.getProductAttributeCategoryId() > 0) {
                    ProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectById(productAttributeListVo.getProductAttributeCategoryId());
                    if (productAttributeCategory != null) {
                        productAttributeListVo.setProductAttributeCategoryName(productAttributeCategory.getName());
                    }
                }
                // 添加人名称
                if (productAttributeListVo.getCreateUser() != null && productAttributeListVo.getCreateUser() > 0) {
                    productAttributeListVo.setCreateUserName(UserUtils.getName((productAttributeListVo.getCreateUser())));
                }
                // 更新人名称
                if (productAttributeListVo.getUpdateUser() != null && productAttributeListVo.getUpdateUser() > 0) {
                    productAttributeListVo.setUpdateUserName(UserUtils.getName((productAttributeListVo.getUpdateUser())));
                }
                productAttributeListVoList.add(productAttributeListVo);
            });
        }
        return JsonResult.success("操作成功", productAttributeListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ProductAttribute entity = (ProductAttribute) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(ProductAttribute entity) {
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
        ProductAttribute entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}