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

package com.javaweb.system.controller;

import com.javaweb.common.config.CommonConfig;
import com.javaweb.system.config.SystemConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.User;
import com.javaweb.system.mapper.UserMapper;
import com.javaweb.system.service.IUserService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.dto.UpdatePasswordDto;
import com.javaweb.system.dto.UpdateUserInfoDto;
import com.javaweb.system.service.IMenuService;
import com.javaweb.system.vo.menu.MenuListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台 控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-04-17
 */
@Controller
public class IndexController {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;

    /**
     * 列表页
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        User user = userMapper.selectById(ShiroUtils.getUserId());
        // 获取菜单
        List<MenuListVo> menuList = menuService.getNavbarMenu(ShiroUtils.getUserId());
        model.addAttribute("menuList", menuList);
        // 用户信息
        user.setAvatar(CommonUtils.getImageURL(user.getAvatar()));
        model.addAttribute("user", user);

        // 系统信息
        model.addAttribute("fullName", SystemConfig.fullName);
        model.addAttribute("nickName", SystemConfig.nickName);
        model.addAttribute("version", SystemConfig.version);
        return "index";
    }

    /**
     * 后台欢迎页
     *
     * @return
     */
    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("version", SystemConfig.version);
        return "main";
    }

    /**
     * 个人中心
     *
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        Map<String, Object> info = userService.info(ShiroUtils.getUserId());
        model.addAttribute("info", info);
        return "userInfo";
    }

    /**
     * 更新个人中心信息
     *
     * @param userInfoDto 参数
     * @return
     */
    @ResponseBody
    @PostMapping("/updateUserInfo")
    public JsonResult updateUserInfo(@RequestBody UpdateUserInfoDto userInfoDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        User entity = new User();
        entity.setId(ShiroUtils.getUserId());
        entity.setNickname(userInfoDto.getNickname());
        entity.setEmail(userInfoDto.getEmail());
        entity.setIntro(userInfoDto.getIntro());
        entity.setAddress(userInfoDto.getAddress());
        Integer result = userMapper.updateById(entity);
        if (result == 0) {
            return JsonResult.error("更新失败");
        }
        return JsonResult.success();
    }

    /**
     * 修改密码
     *
     * @param updatePasswordDto 参数
     * @return
     */
    @ResponseBody
    @PostMapping("/updatePassword")
    public JsonResult updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        // 原密码校验
        if (StringUtils.isEmpty(updatePasswordDto.getOldPassword())) {
            return JsonResult.error("原密码不能为空");
        }
        // 新密码校验
        if (StringUtils.isEmpty(updatePasswordDto.getNewPassword())) {
            return JsonResult.error("新密码不能为空");
        }
        // 确认密码
        if (StringUtils.isEmpty(updatePasswordDto.getRePassword())) {
            return JsonResult.error("确认密码不能为空");
        }
        if (!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getRePassword())) {
            return JsonResult.error("两次输入的密码不一致");
        }
        // 获取当前用户的密码
        User userInfo = userMapper.selectById(ShiroUtils.getUserId());
        if (userInfo == null) {
            return JsonResult.error("用户信息不存在");
        }
        if (!userInfo.getStatus().equals(1)) {
            return JsonResult.error("您的信息已被禁用");
        }
        userInfo.setPassword(CommonUtils.password(updatePasswordDto.getNewPassword()));
        Integer result = userMapper.updateById(userInfo);
        if (result == 0) {
            return JsonResult.error("密码修改失败");
        }
        return JsonResult.success("密码修改成功");
    }
}
