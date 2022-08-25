

package com.javaweb.admin.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 演示案例二 模块常量
 * </p>
 *
 * @author admin
 * @since 2021-10-19
 */
public class Example2Constant {

    /**
     * 类型
     */
    public static Map<Integer, String> EXAMPLE2_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "京东");
            put(2, "淘宝");
            put(3, "拼多多");
            put(4, "唯品会");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> EXAMPLE2_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "正常");
            put(2, "停用");
        }
    };
}