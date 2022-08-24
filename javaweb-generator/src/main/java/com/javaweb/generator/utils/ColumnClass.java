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

package com.javaweb.generator.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据表列
 */
@Data
public class ColumnClass {

    /**
     * 数据库字段名称
     **/
    private String columnName;

    /**
     * 数据库字段类型
     **/
    private String columnType;

    /**
     * 数据库字段首字母小写且去掉下划线字符串
     **/
    private String changeColumnName;

    /**
     * 数据库字段注释
     **/
    private String columnComment;

    /**
     * 是否有注解值
     */
    private boolean hasColumnCommentValue;

    /**
     * 是否选择开关
     */
    private boolean columnSwitch;

    /**
     * 数据库字段注释(仅包含名称)
     **/
    private String columnCommentName;

    /**
     * 数据库字段注解值
     */
    private Map<String, String> columnCommentValue = new HashMap<>();

    /**
     * 字段值(如：1淘宝 2京东 3拼多多,需转换成：1=淘宝,2=京东,3=拼多多)
     */
    private String columnValue;


    /**
     * 字段配置至是否是数字（特殊情况下，值不一定是数字，如：hidden=隐藏）
     */
    private boolean columnNumberValue;

    /**
     * 字段值开关(如：淘宝|京东)
     */
    private String columnSwitchValue;

    /**
     * 字段默认值
     */
    private String columnDefaultValue;

    /**
     * 是否是图片字段
     */
    private boolean columnImage;

    /**
     * 是否是多行文本
     */
    private boolean columnTextArea;

}
