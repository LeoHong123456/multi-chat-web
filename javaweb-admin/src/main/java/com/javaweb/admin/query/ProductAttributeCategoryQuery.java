

package com.javaweb.admin.query;

import com.javaweb.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 产品属性分类查询条件
 * </p>
 *
 * @author admin
 * @since 2020-06-08
 */
@Data
public class ProductAttributeCategoryQuery extends BaseQuery {

    /**
     * 分类名称
     */
    private String name;

}
