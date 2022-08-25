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

package com.javaweb.common.controller;


import com.javaweb.common.common.BaseController;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.service.IUploadService;
import com.javaweb.common.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 角色菜单关联表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-02-26
 */
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {

    @Autowired
    private IUploadService uploadService;

    /**
     * 上传图片
     *
     * @param request 网络请求
     * @param name    目录名
     * @return
     */
    @PostMapping("/uploadImage")
    public JsonResult uploadImage(HttpServletRequest request, String name) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return uploadService.uploadImage(request, name);
    }

    /**
     * 上传文件
     *
     * @param request
     * @param name    目录名
     * @return
     */
    @PostMapping("/uploadFile")
    public JsonResult uploadFile(HttpServletRequest request, String name) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return uploadService.uploadFile(request, name);
    }

    /**
     * 上传图片
     *
     * @param request 网络请求
     * @param name    目录名
     * @return
     */
    @PostMapping("/kindeditorImage")
    public String kindeditorImage(HttpServletRequest request, String name) {
        return uploadService.kindeditorImage(request, name);
    }

}
