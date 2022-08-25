

package com.javaweb.admin.entity;

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
 * 演示案例一
 * </p>
 *
 * @author admin
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_example")
public class Example extends BaseEntity {

    /**
     * 头像
     */
    private String avatar;

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private Integer type;

    /**
     * 是否VIP：1是 2否
     */
    private Integer isVip;

    /**
     * 计划时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date planTime;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String note;

}