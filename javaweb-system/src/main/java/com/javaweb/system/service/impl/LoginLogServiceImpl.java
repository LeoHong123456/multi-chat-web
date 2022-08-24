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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.constant.LoginLogConstant;
import com.javaweb.system.entity.LoginLog;
import com.javaweb.system.mapper.LoginLogMapper;
import com.javaweb.system.query.LoginLogQuery;
import com.javaweb.system.service.ILoginLogService;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.loginlog.LoginLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-04
 */
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LoginLogQuery loginLogQuery = (LoginLogQuery) query;
        // 查询条件
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        // 日志标题
        if (!StringUtils.isEmpty(loginLogQuery.getTitle())) {
            queryWrapper.like("title", loginLogQuery.getTitle());
        }
        // 登录状态：1成功 2失败
        if (loginLogQuery.getStatus() != null && loginLogQuery.getStatus() > 0) {
            queryWrapper.eq("status", loginLogQuery.getStatus());
        }
        // 类型：1登录系统 2退出系统
        if (loginLogQuery.getType() != null && loginLogQuery.getType() > 0) {
            queryWrapper.eq("type", loginLogQuery.getType());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<LoginLog> page = new Page<>(loginLogQuery.getPage(), loginLogQuery.getLimit());
        IPage<LoginLog> data = loginLogMapper.selectPage(page, queryWrapper);
        List<LoginLog> loginLogList = data.getRecords();
        List<LoginLogListVo> loginLogListVoList = new ArrayList<>();
        if (!loginLogList.isEmpty()) {
            loginLogList.forEach(item -> {
                LoginLogListVo loginLogListVo = new LoginLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, loginLogListVo);
                // 登录状态描述
                if (loginLogListVo.getStatus() != null && loginLogListVo.getStatus() > 0) {
                    loginLogListVo.setStatusName(LoginLogConstant.LOGINLOG_STATUS_LIST.get(loginLogListVo.getStatus()));
                }
                // 类型描述
                if (loginLogListVo.getType() != null && loginLogListVo.getType() > 0) {
                    loginLogListVo.setTypeName(LoginLogConstant.LOGINLOG_TYPE_LIST.get(loginLogListVo.getType()));
                }
                // 添加人名称
                if (loginLogListVo.getCreateUser() > 0) {
                    loginLogListVo.setCreateUserName(UserUtils.getName((loginLogListVo.getCreateUser())));
                }
                // 更新人名称
                if (loginLogListVo.getUpdateUser() > 0) {
                    loginLogListVo.setUpdateUserName(UserUtils.getName((loginLogListVo.getUpdateUser())));
                }
                loginLogListVoList.add(loginLogListVo);
            });
        }
        return JsonResult.success("操作成功", loginLogListVoList, data.getTotal());
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        LoginLog entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(LoginLog entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 创建系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }
}