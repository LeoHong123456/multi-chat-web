// +----------------------------------------------------------------------
// | JavaWeb_Layui混编版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站:
// +----------------------------------------------------------------------
// | 作者: admin 
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
 * 广告位描述
 * </p>
 *
 * @author admin
 * @since 2020-05-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_ad_sort")
public class AdSort extends BaseEntity {

    /**
     * 广告位名称
     */
    private String name;

    /**
     * 广告位描述
     */
    private String note;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 广告页面位置
     */
    private Integer locId;

    /**
     * 站点类型：1PC网站 2WAP手机站 3小程序 4APP移动端
     */
    private Integer platform;

    /**
     * 广告位排序
     */
    private Integer sort;

}