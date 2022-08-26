

package com.javaweb.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 商品标签
 * </p>
 *
 * @author admin
 * @since 2020-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_product_tags")
public class ProductTags extends BaseEntity {

    /**
     * 标签名
     */
    private String name;

    /**
     * 状态：1启用 2停用
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer sort;

}