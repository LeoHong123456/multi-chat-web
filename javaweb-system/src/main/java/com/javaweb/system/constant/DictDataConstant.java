

package com.javaweb.system.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 字典数据 模块常量
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
public class DictDataConstant {

    /**
     * 状态
     */
    public static Map<Integer, String> DICT_DATA_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "在用");
            put(2, "停用");
        }
    };
}