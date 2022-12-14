

package com.javaweb.admin.dto;

import lombok.Data;

/**
 * 删除商品SKU设置Dto
 */
@Data
public class DeleteSkuDto {

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * SKU属性值
     */
    private String attributeValue;

}
