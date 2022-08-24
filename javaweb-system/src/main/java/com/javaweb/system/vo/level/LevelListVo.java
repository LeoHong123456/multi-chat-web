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

package com.javaweb.system.vo.level;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaweb.common.annotation.Excel;
import com.javaweb.common.annotation.Excel.ColumnType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 职级列表Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-04-20
 */
@Data
public class LevelListVo {

    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private Integer num;

    /**
     * 职级ID
     */
    @Excel(name = "职级ID", cellType = ColumnType.NUMERIC)
    private Integer id;

    /**
     * 职级名称
     */
    @Excel(name = "职级名称")
    private String name;

    /**
     * 状态：1正常 2停用
     */
    @Excel(name = "职级状态", readConverterExp = "1=正常,2=停用")
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 显示顺序
     */
    @Excel(name = "排序", cellType = ColumnType.NUMERIC)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}