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
import com.javaweb.admin.constant.BrandConstant;
import com.javaweb.admin.entity.Brand;
import com.javaweb.admin.mapper.BrandMapper;
import com.javaweb.admin.query.BrandQuery;
import com.javaweb.admin.service.IBrandService;
import com.javaweb.admin.vo.brand.BrandListVo;
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
 * 商品品牌 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-05
 */
@Service
public class BrandServiceImpl extends BaseServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        BrandQuery brandQuery = (BrandQuery) query;
        // 查询条件
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        // 状态：1启用 2停用
        if (brandQuery.getStatus() != null && brandQuery.getStatus() > 0) {
            queryWrapper.eq("status", brandQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Brand> page = new Page<>(brandQuery.getPage(), brandQuery.getLimit());
        IPage<Brand> data = brandMapper.selectPage(page, queryWrapper);
        List<Brand> brandList = data.getRecords();
        List<BrandListVo> brandListVoList = new ArrayList<>();
        if (!brandList.isEmpty()) {
            brandList.forEach(item -> {
                BrandListVo brandListVo = new BrandListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, brandListVo);
                // 品牌logo地址
                if (!StringUtils.isEmpty(brandListVo.getBrandLogo())) {
                    brandListVo.setBrandLogoUrl(CommonUtils.getImageURL(brandListVo.getBrandLogo()));
                }
                // 专区大图地址
                if (!StringUtils.isEmpty(brandListVo.getBigPic())) {
                    brandListVo.setBigPicUrl(CommonUtils.getImageURL(brandListVo.getBigPic()));
                }
                // 状态描述
                if (brandListVo.getStatus() != null && brandListVo.getStatus() > 0) {
                    brandListVo.setStatusName(BrandConstant.BRAND_STATUS_LIST.get(brandListVo.getStatus()));
                }
                // 添加人名称
                if (brandListVo.getCreateUser() != null && brandListVo.getCreateUser() > 0) {
                    brandListVo.setCreateUserName(UserUtils.getName((brandListVo.getCreateUser())));
                }
                // 更新人名称
                if (brandListVo.getUpdateUser() != null && brandListVo.getUpdateUser() > 0) {
                    brandListVo.setUpdateUserName(UserUtils.getName((brandListVo.getUpdateUser())));
                }
                brandListVoList.add(brandListVo);
            });
        }
        return JsonResult.success("操作成功", brandListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Brand entity = (Brand) super.getInfo(id);
        // 品牌logo解析
        if (!StringUtils.isEmpty(entity.getBrandLogo())) {
            entity.setBrandLogo(CommonUtils.getImageURL(entity.getBrandLogo()));
        }
        // 专区大图解析
        if (!StringUtils.isEmpty(entity.getBigPic())) {
            entity.setBigPic(CommonUtils.getImageURL(entity.getBigPic()));
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
    public JsonResult edit(Brand entity) {
        // 品牌logo
        if (entity.getBrandLogo().contains(CommonConfig.imageURL)) {
            entity.setBrandLogo(entity.getBrandLogo().replaceAll(CommonConfig.imageURL, ""));
        }
        // 专区大图
        if (entity.getBigPic().contains(CommonConfig.imageURL)) {
            entity.setBigPic(entity.getBigPic().replaceAll(CommonConfig.imageURL, ""));
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
        Brand entity = this.getById(id);
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
    public JsonResult setStatus(Brand entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}