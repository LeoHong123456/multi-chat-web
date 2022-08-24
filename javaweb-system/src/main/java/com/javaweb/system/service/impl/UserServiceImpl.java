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

package com.javaweb.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.DateUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.constant.UserConstant;
import com.javaweb.system.entity.*;
import com.javaweb.system.mapper.*;
import com.javaweb.system.query.UserQuery;
import com.javaweb.system.service.ICityService;
import com.javaweb.system.service.IDeptService;
import com.javaweb.system.service.IUserService;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.vo.user.UserInfoVo;
import com.javaweb.system.vo.user.UserListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-01-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LevelMapper levelMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private ICityService cityService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        UserQuery userQuery = (UserQuery) query;
        // 查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 人员姓名/手机号
        if (!StringUtils.isEmpty(userQuery.getKeywords())) {
            queryWrapper.like("realname", userQuery.getKeywords()).or().like("mobile", userQuery.getKeywords());
        }
        // 性别:1男 2女 3保密
        if (userQuery.getGender() != null) {
            queryWrapper.eq("gender", userQuery.getGender());
        }
        // 状态：1正常 2禁用
        if (userQuery.getStatus() != null) {
            queryWrapper.eq("status", userQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询数据
        IPage<User> page = new Page<>(userQuery.getPage(), userQuery.getLimit());
        IPage<User> data = userMapper.selectPage(page, queryWrapper);
        List<User> userList = data.getRecords();
        List<UserListVo> userListVoList = new ArrayList<>();
        if (!userList.isEmpty()) {
            userList.forEach(item -> {
                UserListVo userListVo = new UserListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, userListVo);
                // 性别描述
                if (userListVo.getGender() != null && userListVo.getGender() > 0) {
                    userListVo.setGenderName(UserConstant.USER_GENDER_LIST.get(userListVo.getGender()));
                }
                // 头像地址
                if (!StringUtils.isEmpty(userListVo.getAvatar())) {
                    userListVo.setAvatarUrl(CommonUtils.getImageURL(userListVo.getAvatar()));
                }
                // 状态描述
                if (userListVo.getStatus() != null && userListVo.getStatus() > 0) {
                    userListVo.setStatusName(UserConstant.USER_STATUS_LIST.get(userListVo.getStatus()));
                }
                // 所属部门
                if (userListVo.getDeptId() != null && userListVo.getDeptId() > 0) {
                    String deptName = deptService.getDeptNameById(userListVo.getDeptId(), ">>");
                    userListVo.setDeptName(deptName);
                }
                // 获取职级
                Level levelInfo = levelMapper.selectById(item.getLevelId());
                if (levelInfo != null) {
                    userListVo.setLevelName(levelInfo.getName());
                }
                // 获取岗位
                Position positionInfo = positionMapper.selectById(item.getPositionId());
                if (positionInfo != null) {
                    userListVo.setPositionName(positionInfo.getName());
                }
                // 获取所属城市名称
                String cityName = cityService.getCityNameByCityId(userListVo.getDistrictId(), ">>");
                userListVo.setCityName(cityName);
                // 添加人名称
                if (userListVo.getCreateUser() > 0) {
                    userListVo.setCreateUserName(UserUtils.getName((userListVo.getCreateUser())));
                }
                // 更新人名称
                if (userListVo.getUpdateUser() > 0) {
                    userListVo.setUpdateUserName(UserUtils.getName((userListVo.getUpdateUser())));
                }
                userListVoList.add(userListVo);
            });
        }
        return JsonResult.success("操作成功", userListVoList, data.getTotal());
    }

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Map<String, Object> info(Integer id) {
        Object entity = this.getInfo(id);
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(entity), new TypeReference<Map<String, Object>>() {
        });
        return map;
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        User entity = (User) getById(id);
        // 拷贝属性
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(entity, userInfoVo);
        // 头像解析
        if (!StringUtils.isEmpty(entity.getAvatar())) {
            userInfoVo.setAvatar(CommonUtils.getImageURL(entity.getAvatar()));
        }
        // 所属城市
        if (entity.getDistrictId() != null && entity.getDistrictId() > 0) {
            String cityName = cityService.getCityNameByCityId(entity.getDistrictId(), " ");
            userInfoVo.setCityName(cityName);
        }
        // 获取部门
        if (userInfoVo.getDeptId() != null && userInfoVo.getDeptId() > 0) {
            String deptName = deptService.getDeptNameById(userInfoVo.getDeptId(), " -> ");
            userInfoVo.setDeptName(deptName);
        }
        return userInfoVo;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(User entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        // 头像
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        // 设置密码
        if (!StringUtils.isEmpty(entity.getPassword())) {
            entity.setPassword(CommonUtils.password(entity.getPassword()));
        } else {
            entity.setPassword(null);
        }
        // 附件处理
        if (StringUtils.isNotEmpty(entity.getFiles())) {
            entity.setFiles(entity.getFiles().replaceAll(CommonConfig.imageURL, ""));
        }
        boolean result = false;
        if (entity.getId() != null && entity.getId() > 0) {
            // 修改记录
            // 判断是否用户名已存在
            Integer count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, entity.getUsername())
                    .ne(User::getId, entity.getId())
                    .eq(User::getMark, 1));
            if (count > 0) {
                return JsonResult.error("当前用户名已存在");
            }
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
            result = this.updateById(entity);

        } else {
            // 新增记录
            // 判断是否用户名已存在
            Integer count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, entity.getUsername())
                    .eq(User::getMark, 1));
            if (count > 0) {
                return JsonResult.error("当前用户名已存在");
            }
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
            entity.setMark(1);
            result = this.save(entity);
        }
        if (!result) {
            return JsonResult.error();
        }

        // 删除人员角色关系
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, entity.getId()));
        // 创建人员角色关系
        if (StringUtils.isNotEmpty(entity.getRoleIds())) {
            String[] strings = entity.getRoleIds().split(",");
            for (String string : strings) {
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());
                userRole.setRoleId(Integer.valueOf(string));
                userRole.setCreateUser(ShiroUtils.getUserId());
                userRole.setCreateTime(DateUtils.now());
                userRole.setUpdateUser(ShiroUtils.getUserId());
                userRole.setUpdateTime(DateUtils.now());
                userRoleMapper.insert(userRole);
            }
        }


        return JsonResult.success();
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        User entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        entity.setUpdateUser(ShiroUtils.getUserId());
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    @Override
    public JsonResult deleteByIds(String ids) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (StringUtils.isEmpty(ids)) {
            return JsonResult.error("记录ID不能为空");
        }
        String[] item = ids.split(",");
        // 设置Mark=0
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("mark", 0);
        updateWrapper.in("id", item);
        boolean result = update(updateWrapper);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(User entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        entity.setUpdateUser(ShiroUtils.getUserId());
        entity.setUpdateTime(DateUtils.now());
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    /**
     * 根据用户名获取人员
     *
     * @param username 用户名
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("mark", 1);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

}
