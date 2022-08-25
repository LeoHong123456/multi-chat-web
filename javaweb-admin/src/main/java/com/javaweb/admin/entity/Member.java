

package com.javaweb.admin.entity;

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
 * @author admin
 * @since 2021-01-28
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
