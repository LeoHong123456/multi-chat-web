

package com.javaweb.system.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 部门 模块常量
 * </p>
 *
 * @author admin
 * @since 2020-05-03
 */
public class DeptConstant {

    /**
     * 类型
     */
    public static Map<Integer, String> DEPT_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "公司");
            put(2, "子公司");
            put(3, "部门");
            put(4, "小组");
        }
    };
    /**
     * 是否有子级
     */
    public static Map<Integer, String> DEPT_HASCHILD_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
}