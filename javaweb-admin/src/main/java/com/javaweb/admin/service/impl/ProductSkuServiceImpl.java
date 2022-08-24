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
import com.javaweb.admin.constant.ProductSkuConstant;
import com.javaweb.admin.entity.ProductSku;
import com.javaweb.admin.mapper.ProductSkuMapper;
import com.javaweb.admin.query.ProductSkuQuery;
import com.javaweb.admin.service.IProductSkuService;
import com.javaweb.admin.vo.productsku.ProductSkuListVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品SKU 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-06-11
 */
@Service
public class ProductSkuServiceImpl extends BaseServiceImpl<ProductSkuMapper, ProductSku> implements IProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ProductSkuQuery productSkuQuery = (ProductSkuQuery) query;
        // 查询条件
        QueryWrapper<ProductSku> queryWrapper = new QueryWrapper<>();
        // 活动类型：1商品 2秒杀 3砍价 4拼团
        if (productSkuQuery.getType() != null && productSkuQuery.getType() > 0) {
            queryWrapper.eq("type", productSkuQuery.getType());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<ProductSku> page = new Page<>(productSkuQuery.getPage(), productSkuQuery.getLimit());
        IPage<ProductSku> data = productSkuMapper.selectPage(page, queryWrapper);
        List<ProductSku> productSkuList = data.getRecords();
        List<ProductSkuListVo> productSkuListVoList = new ArrayList<>();
        if (!productSkuList.isEmpty()) {
            productSkuList.forEach(item -> {
                ProductSkuListVo productSkuListVo = new ProductSkuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, productSkuListVo);
                // 图片地址
                if (!StringUtils.isEmpty(productSkuListVo.getImage())) {
                    productSkuListVo.setImageUrl(CommonUtils.getImageURL(productSkuListVo.getImage()));
                }
                // 活动类型描述
                if (productSkuListVo.getType() != null && productSkuListVo.getType() > 0) {
                    productSkuListVo.setTypeName(ProductSkuConstant.PRODUCTSKU_TYPE_LIST.get(productSkuListVo.getType()));
                }
                // 添加人名称
                if (productSkuListVo.getCreateUser() != null && productSkuListVo.getCreateUser() > 0) {
                    productSkuListVo.setCreateUserName(UserUtils.getName((productSkuListVo.getCreateUser())));
                }
                // 更新人名称
                if (productSkuListVo.getUpdateUser() != null && productSkuListVo.getUpdateUser() > 0) {
                    productSkuListVo.setUpdateUserName(UserUtils.getName((productSkuListVo.getUpdateUser())));
                }
                productSkuListVoList.add(productSkuListVo);
            });
        }
        return JsonResult.success("操作成功", productSkuListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ProductSku entity = (ProductSku) super.getInfo(id);
        // 图片解析
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
    public JsonResult edit(ProductSku entity) {
        // 图片
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
        ProductSku entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 根据商品ID获取SKU列表
     *
     * @param productId 商品ID
     * @return
     */
    @Override
    public List<ProductSku> getProductSkuList(Integer productId) {
        QueryWrapper<ProductSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("id");
        List<ProductSku> productSkuList = productSkuMapper.selectList(queryWrapper);
        return productSkuList;
    }

    /**
     * 根据商品ID删除SKU列表
     *
     * @param productId 商品ID
     */
    @Override
    public void deleteProductSkuByProductId(Integer productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        productSkuMapper.deleteByMap(map);
    }
}