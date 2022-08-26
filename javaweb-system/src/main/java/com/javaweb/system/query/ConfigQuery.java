

package com.javaweb.system.query;

import com.javaweb.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 系统配置查询条件
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
@Data
public class ConfigQuery extends BaseQuery {

    /**
     * 分组名称
     */
    private String name;

}
