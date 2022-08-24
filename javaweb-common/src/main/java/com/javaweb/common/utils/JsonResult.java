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

package com.javaweb.common.utils;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * JSON回应类
 *
 * @author 牧羊人
 * @date 2019/11/28
 */
public class JsonResult implements Serializable {
    // 错误码
    private Integer code = 0;

    // 提示语
    private String msg = "操作成功";

    // 返回对象
    private Object data;

    // 数据总数
    private long count;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(final long count) {
        this.count = count;
    }

    /**
     * 构造函数
     */
    public JsonResult() {
    }

    public JsonResult(String msg) {
        this.msg = msg;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Integer code, String msg, Object data, long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public static JsonResult success() {
        return new JsonResult();
    }

    public static JsonResult success(String msg) {
        return new JsonResult(msg);
    }

    public JsonResult success(Object data) {
        return new JsonResult(0, msg, data);
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(0, msg, data);
    }

    public static JsonResult success(String msg, Object data, long count) {
        return new JsonResult(0, msg, data, count);
    }


    public static JsonResult error() {
        return new JsonResult(-1, "操作失败");
    }

    public static JsonResult error(String msg) {
        return new JsonResult(401, msg);
    }

    public static JsonResult error(Integer code, String msg) {
        return new JsonResult(code, msg);
    }

    public static JsonResult error(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult error(ErrorCode errorCode) {
        return new JsonResult(errorCode.getCode(), errorCode.getMsg());
    }

    public static JsonResult error(HttpStatus httpStatus, String msg, Object data) {
        return new JsonResult(httpStatus.value(), msg, data);
    }

    public Object error(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
        return this;
    }

}
