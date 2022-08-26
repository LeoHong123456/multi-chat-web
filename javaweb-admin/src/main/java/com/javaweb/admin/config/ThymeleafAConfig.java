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

package com.javaweb.admin.config;

import com.javaweb.admin.constant.*;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Thymeleaf模板配置
 */
@Configuration
public class ThymeleafAConfig {

    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();

            /**
             * 会员性别
             */
            vars.put("MEMBER_GENDER_LIST", MemberConstant.MEMBER_GENDER_LIST);
            /**
             * 会员设备类型
             */
            vars.put("MEMBER_DEVICE_LIST", MemberConstant.MEMBER_DEVICE_LIST);
            /**
             * 会员登陆状态
             */
            vars.put("MEMBER_LOGINSTATUS_LIST", MemberConstant.MEMBER_LOGINSTATUS_LIST);
            /**
             * 会员注册来源
             */
            vars.put("MEMBER_SOURCE_LIST", MemberConstant.MEMBER_SOURCE_LIST);
            /**
             * 会员状态
             */
            vars.put("MEMBER_STATUS_LIST", MemberConstant.MEMBER_STATUS_LIST);
            /**
             * 品牌状态
             */
            vars.put("BRAND_STATUS_LIST", BrandConstant.BRAND_STATUS_LIST);
            /**
             * 商品标签状态
             */
            vars.put("PRODUCTTAGS_STATUS_LIST", ProductTagsConstant.PRODUCTTAGS_STATUS_LIST);
            /**
             * 商品分类状态
             */
            vars.put("PRODUCTCATEGORY_STATUS_LIST", ProductCategoryConstant.PRODUCTCATEGORY_STATUS_LIST);
            /**
             * 商品属性类型
             */
            vars.put("PRODUCTATTRIBUTE_TYPE_LIST", ProductAttributeConstant.PRODUCTATTRIBUTE_TYPE_LIST);
            /**
             * 商品是否热卖
             */
            vars.put("PRODUCT_ISHOT_LIST", ProductConstant.PRODUCT_ISHOT_LIST);
            /**
             * 商品是否推荐
             */
            vars.put("PRODUCT_ISRECOMMAND_LIST", ProductConstant.PRODUCT_ISRECOMMAND_LIST);
            /**
             * 商品是否新品
             */
            vars.put("PRODUCT_ISNEW_LIST", ProductConstant.PRODUCT_ISNEW_LIST);
            /**
             * 商品审核状态
             */
            vars.put("PRODUCT_VERIFYSTATUS_LIST", ProductConstant.PRODUCT_VERIFYSTATUS_LIST);
            /**
             * 商品上下架状态
             */
            vars.put("PRODUCT_STATUS_LIST", ProductConstant.PRODUCT_STATUS_LIST);
            /**
             * 商品促销类型
             */
            vars.put("PRODUCT_PROMOTIONTYPE_LIST", ProductConstant.PRODUCT_PROMOTIONTYPE_LIST);
            viewResolver.setStaticVariables(vars);
        }
    }
}
