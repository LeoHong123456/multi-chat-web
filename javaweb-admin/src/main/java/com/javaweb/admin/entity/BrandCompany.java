

package com.javaweb.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 品牌商
 * </p>
 *
 * @author admin
 * @since 2020-05-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_brand_company")
public class BrandCompany extends BaseEntity {

    /**
     * 品牌商名称
     */
    private String name;

    /**
     * 品牌商简介
     */
    private String description;

    /**
     * 品牌商页的品牌商图片
     */
    private String image;

    /**
     * 排序号
     */
    private Integer sort;

}