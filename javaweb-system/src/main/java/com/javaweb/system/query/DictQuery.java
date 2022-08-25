

package com.javaweb.system.query;

import com.javaweb.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 字典查询条件
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
@Data
public class DictQuery extends BaseQuery {

    /**
     * 字典名称
     */
    private String name;

}
