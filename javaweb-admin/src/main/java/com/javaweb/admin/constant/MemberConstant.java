// +----------------------------------------------------------------------
// | JavaWeb_Layui混编版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站: http://www.javaweb.vip/
// +----------------------------------------------------------------------
// | 作者: 鲲鹏 <javaweb520@gmail.com>
// +----------------------------------------------------------------------
// | 免责声明:
// | 本软件框架禁止任何单位和个人用于任何违法、侵害他人合法利益等恶意的行为，禁止用于任何违
// | 反我国法律法规的一切平台研发，任何单位和个人使用本软件框架用于产品研发而产生的任何意外
// |  、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、附带
// | 或衍生的损失等)，本团队不承担任何法律责任。本软件框架已申请版权保护，任何组织、单位和个
// | 人不得有任何侵犯我们的版权的行为(包括但不限于分享、转售、恶意传播，开源等等)，否则产生
// | 的一切后果和损失由侵权者全部承担，本软件框架只能用于公司和个人内部的法律所允许的合法合
// | 规的软件产品研发，详细声明内容请阅读《框架免责声明》附件；
// +----------------------------------------------------------------------

package com.javaweb.admin.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员用户 模块常量
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-04
 */
public class MemberConstant {

    /**
     * 性别
     */
    public static Map<Integer, String> MEMBER_GENDER_LIST = new HashMap<Integer, String>() {
        {
            put(1, "男");
            put(2, "女");
            put(3, "未知");
        }
    };
    /**
     * 设备类型
     */
    public static Map<Integer, String> MEMBER_DEVICE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "苹果");
            put(2, "安卓");
            put(3, "WAP站");
            put(4, "PC站");
            put(5, "微信小程序");
            put(6, "后台添加");
        }
    };
    /**
     * 用户状态
     */
    public static Map<Integer, String> MEMBER_LOGINSTATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "登录");
            put(2, "登出");
        }
    };
    /**
     * 用户来源
     */
    public static Map<Integer, String> MEMBER_SOURCE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "注册会员");
            put(2, "马甲会员");
        }
    };
    /**
     * 是否启用
     */
    public static Map<Integer, String> MEMBER_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "启用");
            put(2, "停用");
        }
    };
}