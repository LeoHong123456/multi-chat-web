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

package com.javaweb.generator.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 代码生成列列表Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-10
 */
@Data
public class GenTableColumnListVo {

    /**
     * 代码生成列ID
     */
    private Integer id;

    /**
     * 归属表编号
     */
    private Integer tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键：1是 2否
     */
    private Integer isPk;

    /**
     * 是否主键描述
     */
    private String isPkName;

    /**
     * 是否自增：1是 2否
     */
    private Integer isIncrement;

    /**
     * 是否自增描述
     */
    private String isIncrementName;

    /**
     * 是否必填：1是 2否
     */
    private Integer isRequired;

    /**
     * 是否必填描述
     */
    private String isRequiredName;

    /**
     * 是否为插入字段：1是 2否
     */
    private Integer isInsert;

    /**
     * 是否为插入字段描述
     */
    private String isInsertName;

    /**
     * 是否编辑字段：1是 2否
     */
    private Integer isEdit;

    /**
     * 是否编辑字段描述
     */
    private String isEditName;

    /**
     * 是否列表字段：1是 2否
     */
    private Integer isList;

    /**
     * 是否列表字段描述
     */
    private String isListName;

    /**
     * 是否查询字段：1是 2否
     */
    private Integer isQuery;

    /**
     * 是否查询字段描述
     */
    private String isQueryName;

    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    private String queryType;

    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

}