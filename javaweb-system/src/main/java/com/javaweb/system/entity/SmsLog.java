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

package com.javaweb.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * <p>
 * 短信日志
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_sms_log")
public class SmsLog extends BaseEntity {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 发送类型：1用户注册 2修改密码 3找回密码 4换绑手机号验证 5换绑手机号 6钱包提现 7设置支付密码 8系统通知
     */
    private Integer type;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 项目签名
     */
    private String sign;

    /**
     * 
     */
    private String templateCode;

    /**
     * 参数
     */
    private String params;

    /**
     * 阿里云返回的
     */
    private String bizId;

    /**
     * 阿里云返回的code
     */
    private String code;

    /**
     * 阿里云返回的
     */
    private String message;

    /**
     * 状态：1成功 2失败 3待处理
     */
    private Integer status;

}