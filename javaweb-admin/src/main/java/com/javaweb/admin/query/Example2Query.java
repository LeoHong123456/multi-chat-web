

package com.javaweb.admin.query;

import com.javaweb.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 演示案例二查询条件
 * </p>
 *
 * @author admin
 * @since 2021-10-19
 */
@Data
public class Example2Query extends BaseQuery {

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private Integer type;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

}
