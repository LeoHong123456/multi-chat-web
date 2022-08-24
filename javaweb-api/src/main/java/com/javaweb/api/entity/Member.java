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

package com.javaweb.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_member")
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别：1男 2女 3未知
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 户籍省份编号
     */
    private Integer provinceId;

    /**
     * 户籍城市编号
     */
    private Integer cityId;

    /**
     * 户籍区/县编号
     */
    private Integer districtId;

    /**
     * 所在城市
     */
    private String cityArea;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 设备类型：1苹果 2安卓 3WAP站 4PC站 5微信小程序 6后台添加
     */
    private Integer device;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 用户状态：1登录 2登出
     */
    private Integer loginStatus;

    /**
     * 推送的别名
     */
    private String pushAlias;

    /**
     * 用户来源：1注册会员 2马甲会员
     */
    private Integer source;

    /**
     * 是否启用：1启用  2停用
     */
    private Integer status;

    /**
     * 会员等级
     */
    private Integer memberLevel;

    /**
     * 客户端版本号
     */
    private String appVersion;

    /**
     * 我的推广码
     */
    private String code;

    /**
     * 推广二维码路径
     */
    private String qrcode;

    /**
     * 最近登录IP
     */
    private String loginIp;

    /**
     * 上次登录地点
     */
    private String loginRegion;

    /**
     * 最近登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 登录总次数
     */
    private Integer loginCount;

}
