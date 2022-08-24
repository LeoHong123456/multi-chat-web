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

package com.javaweb.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


/**
 * <p>
 * 商品SKU
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_product_sku")
public class ProductSku extends BaseEntity {

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品图片
     */
    private String productPic;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * 商品属性索引值 (attribute_value,attribute_value[,....])
     */
    private String attributeValue;

    /**
     * 属性对应的库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 预警库存
     */
    private Integer lowStock;

    /**
     * 锁定库存
     */
    private Integer lockStock;

    /**
     * 属性金额
     */
    private BigDecimal price;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 图片
     */
    private String image;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

    /**
     * 活动类型：1商品 2秒杀 3砍价 4拼团
     */
    private Integer type;

    /**
     * 活动限购数量
     */
    private Integer quota;

    /**
     * 所属店铺ID
     */
    private Integer storeId;

}