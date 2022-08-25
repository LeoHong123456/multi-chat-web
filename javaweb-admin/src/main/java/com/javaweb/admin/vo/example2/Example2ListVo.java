

package com.javaweb.admin.vo.example2;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 演示案例二列表Vo
 * </p>
 *
 * @author admin
 * @since 2021-10-19
 */
@Data
public class Example2ListVo {

    /**
     * 演示案例二ID
     */
    private Integer id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 案例名称
     */
    private String name;

    /**
     * 类型：1京东 2淘宝 3拼多多 4唯品会
     */
    private Integer type;

    /**
     * 类型描述
     */
    private String typeName;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String note;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加人名称
     */
    private String createUserName;

    /**
     * 添加时间
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