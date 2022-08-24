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
import com.javaweb.system.constant.SmsLogConstant;
import com.javaweb.system.entity.SmsLog;
import com.javaweb.system.mapper.SmsLogMapper;
import com.javaweb.system.query.SmsLogQuery;
import com.javaweb.system.service.ISmsLogService;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.smslog.SmsLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 短信日志 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-04
 */
@Service
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLogMapper, SmsLog> implements ISmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        SmsLogQuery smsLogQuery = (SmsLogQuery) query;
        // 查询条件
        QueryWrapper<SmsLog> queryWrapper = new QueryWrapper<>();
        // 手机号码
        if (!StringUtils.isEmpty(smsLogQuery.getMobile())) {
            queryWrapper.like("mobile", smsLogQuery.getMobile());
        }
        // 发送类型：1用户注册 2修改密码 3找回密码 4换绑手机号验证 5换绑手机号 6钱包提现 7设置支付密码 8系统通知
        if (smsLogQuery.getType() != null && smsLogQuery.getType() > 0) {
            queryWrapper.eq("type", smsLogQuery.getType());
        }
        // 状态：1成功 2失败 3待处理
        if (smsLogQuery.getStatus() != null && smsLogQuery.getStatus() > 0) {
            queryWrapper.eq("status", smsLogQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<SmsLog> page = new Page<>(smsLogQuery.getPage(), smsLogQuery.getLimit());
        IPage<SmsLog> data = smsLogMapper.selectPage(page, queryWrapper);
        List<SmsLog> smsLogList = data.getRecords();
        List<SmsLogListVo> smsLogListVoList = new ArrayList<>();
        if (!smsLogList.isEmpty()) {
            smsLogList.forEach(item -> {
                SmsLogListVo smsLogListVo = new SmsLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, smsLogListVo);
                // 发送类型描述
                if (smsLogListVo.getType() != null && smsLogListVo.getType() > 0) {
                    smsLogListVo.setTypeName(SmsLogConstant.SMSLOG_TYPE_LIST.get(smsLogListVo.getType()));
                }
                // 状态描述
                if (smsLogListVo.getStatus() != null && smsLogListVo.getStatus() > 0) {
                    smsLogListVo.setStatusName(SmsLogConstant.SMSLOG_STATUS_LIST.get(smsLogListVo.getStatus()));
                }
                // 添加人名称
                if (smsLogListVo.getCreateUser() > 0) {
                    smsLogListVo.setCreateUserName(UserUtils.getName((smsLogListVo.getCreateUser())));
                }
                // 更新人名称
                if (smsLogListVo.getUpdateUser() > 0) {
                    smsLogListVo.setUpdateUserName(UserUtils.getName((smsLogListVo.getUpdateUser())));
                }
                smsLogListVoList.add(smsLogListVo);
            });
        }
        return JsonResult.success("操作成功", smsLogListVoList, data.getTotal());
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
        SmsLog entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}