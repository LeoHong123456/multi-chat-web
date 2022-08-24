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

package com.javaweb.system.vo.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单列表Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-07
 */
@Data
public class MenuListVo {

    /**
     * 菜单ID
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * URL地址
     */
    private String url;

    /**
     * 参数
     */
    private String param;

    /**
     * 上级ID
     */
    private Integer pid;

    /**
     * 类型：1模块 2导航 3菜单 4节点
     */
    private Integer type;

    /**
     * 类型描述
     */
    private String typeName;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 打开方式：1内链 2外链
     */
    private Integer target;

    /**
     * 是否显示：1显示 2不显示
     */
    private Integer status;

    /**
     * 是否显示描述
     */
    private String statusName;

    /**
     * 是否公共：1是 2否
     */
    private Integer isPublic;

    /**
     * 是否公共描述
     */
    private String isPublicName;

    /**
     * 菜单备注
     */
    private String note;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建人名称
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

    /**
     * 子级菜单
     */
    private List<MenuListVo> children;

    /**
     * 是否有子级
     */
    private boolean haveChild;

}