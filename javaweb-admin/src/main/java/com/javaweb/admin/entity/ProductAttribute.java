

package com.javaweb.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author admin
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_product_attribute")
public class ProductAttribute extends BaseEntity {

    /**
     * 商品属性分类ID
     */
    private Integer productAttributeCategoryId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性的类型：1规格 2属性
     */
    private Integer type;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 所属店铺ID
     */
    private Integer storeId;

}