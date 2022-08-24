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

package com.javaweb.system.config;

import com.javaweb.system.constant.*;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Thymeleaf模板配置
 */
@Configuration
public class ThymeleafConfig {
    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            /**
             * 职级状态
             */
            vars.put("LEVEL_STATUS_LIST", LevelConstant.LEVEL_STATUS_LIST);
            /**
             * 岗位状态
             */
            vars.put("POSITION_STATUS_LIST", PositionConstant.POSITION_STATUS_LIST);
            /**
             * 站点类型
             */
            vars.put("ITEM_TYPE_LIST", ItemConstant.ITEM_TYPE_LIST);
            /**
             * 栏目有无图片
             */
            vars.put("ITEMCATE_ISCOVER_LIST", ItemCateConstant.ITEMCATE_ISCOVER_LIST);
            /**
             * 栏目状态
             */
            vars.put("ITEMCATE_STATUS_LIST", ItemCateConstant.ITEMCATE_STATUS_LIST);
            /**
             * 站点域名是否是二级域名
             */
            vars.put("ITEM_ISDOMAIN_LIST", ItemConstant.ITEM_ISDOMAIN_LIST);
            /**
             * 站点状态
             */
            vars.put("ITEM_STATUS_LIST", ItemConstant.ITEM_STATUS_LIST);
            /**
             * 角色状态
             */
            vars.put("ROLE_STATUS_LIST", RoleConstant.ROLE_STATUS_LIST);

            /**
             * 广告位类型
             */
            vars.put("ADSORT_PLATFORM_LIST", AdSortConstant.ADSORT_PLATFORM_LIST);
            /**
             * 广告类型
             */
            vars.put("AD_TYPE_LIST", AdConstant.AD_TYPE_LIST);
            /**
             * 广告状态
             */
            vars.put("AD_STATUS_LIST", AdConstant.AD_STATUS_LIST);
            /**
             * 人员性别
             */
            vars.put("USER_GENDER_LIST", UserConstant.USER_GENDER_LIST);
            /**
             * 人员状态
             */
            vars.put("USER_STATUS_LIST", UserConstant.USER_STATUS_LIST);
            /**
             * 部门类型
             */
            vars.put("DEPT_TYPE_LIST", DeptConstant.DEPT_TYPE_LIST);
            /**
             * 部门是否有子级
             */
            vars.put("DEPT_HASCHILD_LIST", DeptConstant.DEPT_HASCHILD_LIST);
            /**
             * 城市级别
             */
            vars.put("CITY_LEVEL_LIST", CityConstant.CITY_LEVEL_LIST);
            /**
             * 配置状态
             */
            vars.put("CONFIG_DATA_STATUS_LIST", ConfigDataConstant.CONFIG_DATA_STATUS_LIST);
            /**
             * 配置类型
             */
            vars.put("CONFIG_DATA_TYPE_LIST", ConfigDataConstant.CONFIG_DATA_TYPE_LIST);
            /**
             * 字典状态
             */
            vars.put("DICT_DATA_STATUS_LIST", DictDataConstant.DICT_DATA_STATUS_LIST);
            /**
             * 布局类型
             */
            vars.put("LAYOUT_TYPE_LIST", LayoutConstant.LAYOUT_TYPE_LIST);
            /**
             * 友链类型
             */
            vars.put("LINK_TYPE_LIST", LinkConstant.LINK_TYPE_LIST);
            /**
             * 友链平台
             */
            vars.put("LINK_PLATFORM_LIST", LinkConstant.LINK_PLATFORM_LIST);
            /**
             * 友链形式
             */
            vars.put("LINK_FORM_LIST", LinkConstant.LINK_FORM_LIST);
            /**
             * 友链状态
             */
            vars.put("LINK_STATUS_LIST", LinkConstant.LINK_STATUS_LIST);
            /**
             * 登录日志状态
             */
            vars.put("LOGINLOG_STATUS_LIST", LoginLogConstant.LOGINLOG_STATUS_LIST);
            /**
             * 登录日志类型
             */
            vars.put("LOGINLOG_TYPE_LIST", LoginLogConstant.LOGINLOG_TYPE_LIST);
            /**
             * 消息模板类型
             */
            vars.put("MESSAGETEMPLATE_TYPE_LIST", MessageTemplateConstant.MESSAGETEMPLATE_TYPE_LIST);
            /**
             * 消息模板状态
             */
            vars.put("MESSAGETEMPLATE_STATUS_LIST", MessageTemplateConstant.MESSAGETEMPLATE_STATUS_LIST);
            /**
             * 消息类型
             */
            vars.put("MESSAGE_TYPE_LIST", MessageConstant.MESSAGE_TYPE_LIST);
            /**
             * 消息发送状态
             */
            vars.put("MESSAGE_SENDSTATUS_LIST", MessageConstant.MESSAGE_SENDSTATUS_LIST);
            /**
             * 通知公告来源
             */
            vars.put("NOTICE_SOURCE_LIST", NoticeConstant.NOTICE_SOURCE_LIST);
            /**
             * 通知公告是否已置顶
             */
            vars.put("NOTICE_ISTOP_LIST", NoticeConstant.NOTICE_ISTOP_LIST);
            /**
             * 通知公告是否已发布
             */
            vars.put("NOTICE_STATUS_LIST", NoticeConstant.NOTICE_STATUS_LIST);
            /**
             * 通知公告是否已推送
             */
            vars.put("NOTICE_ISSEND_LIST", NoticeConstant.NOTICE_ISSEND_LIST);
            /**
             * 操作日志业务类型
             */
            vars.put("OPERLOG_BUSINESSTYPE_LIST", OperLogConstant.OPERLOG_BUSINESSTYPE_LIST);
            /**
             * 操作日志操作类别
             */
            vars.put("OPERLOG_OPERATORTYPE_LIST", OperLogConstant.OPERLOG_OPERATORTYPE_LIST);
            /**
             * 操作日志状态
             */
            vars.put("OPERLOG_STATUS_LIST", OperLogConstant.OPERLOG_STATUS_LIST);
            /**
             * 短信发送类型
             */
            vars.put("SMSLOG_TYPE_LIST", SmsLogConstant.SMSLOG_TYPE_LIST);
            /**
             * 短信发送状态
             */
            vars.put("SMSLOG_STATUS_LIST", SmsLogConstant.SMSLOG_STATUS_LIST);
            /**
             * 菜单类型
             */
            vars.put("MENU_TYPE_LIST", MenuConstant.MENU_TYPE_LIST);
            /**
             * 菜单显示状态
             */
            vars.put("MENU_STATUS_LIST", MenuConstant.MENU_STATUS_LIST);
            /**
             * 是否公共菜单
             */
            vars.put("MENU_ISPUBLIC_LIST", MenuConstant.MENU_ISPUBLIC_LIST);
            viewResolver.setStaticVariables(vars);
        }
    }
}
