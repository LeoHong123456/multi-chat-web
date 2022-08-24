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
import com.javaweb.system.constant.OperLogConstant;
import com.javaweb.system.entity.OperLog;
import com.javaweb.system.mapper.OperLogMapper;
import com.javaweb.system.query.OperLogQuery;
import com.javaweb.system.service.IOperLogService;
import com.javaweb.system.vo.operlog.OperLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-04
 */
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        OperLogQuery operLogQuery = (OperLogQuery) query;
        // 查询条件
        QueryWrapper<OperLog> queryWrapper = new QueryWrapper<>();
        // 模块标题
        if (!StringUtils.isEmpty(operLogQuery.getTitle())) {
            queryWrapper.like("title", operLogQuery.getTitle());
        }
        // 业务类型：0其它 1新增 2修改 3删除
        if (operLogQuery.getBusinessType() != null && operLogQuery.getBusinessType() > 0) {
            queryWrapper.eq("business_type", operLogQuery.getBusinessType());
        }
        // 操作类别：0其它 1后台用户 2手机端用户
        if (operLogQuery.getOperatorType() != null && operLogQuery.getOperatorType() > 0) {
            queryWrapper.eq("operator_type", operLogQuery.getOperatorType());
        }
        // 操作状态：1正常 2异常
        if (operLogQuery.getStatus() != null && operLogQuery.getStatus() > 0) {
            queryWrapper.eq("status", operLogQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<OperLog> page = new Page<>(operLogQuery.getPage(), operLogQuery.getLimit());
        IPage<OperLog> data = operLogMapper.selectPage(page, queryWrapper);
        List<OperLog> operLogList = data.getRecords();
        List<OperLogListVo> operLogListVoList = new ArrayList<>();
        if (!operLogList.isEmpty()) {
            operLogList.forEach(item -> {
                OperLogListVo operLogListVo = new OperLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, operLogListVo);
                // 业务类型描述
                if (operLogListVo.getBusinessType() != null && operLogListVo.getBusinessType() > 0) {
                    operLogListVo.setBusinessTypeName(OperLogConstant.OPERLOG_BUSINESSTYPE_LIST.get(operLogListVo.getBusinessType()));
                }
                // 操作类别描述
                if (operLogListVo.getOperatorType() != null && operLogListVo.getOperatorType() > 0) {
                    operLogListVo.setOperatorTypeName(OperLogConstant.OPERLOG_OPERATORTYPE_LIST.get(operLogListVo.getOperatorType()));
                }
                // 操作状态描述
                if (operLogListVo.getStatus() != null && operLogListVo.getStatus() > 0) {
                    operLogListVo.setStatusName(OperLogConstant.OPERLOG_STATUS_LIST.get(operLogListVo.getStatus()));
                }
                operLogListVoList.add(operLogListVo);
            });
        }
        return JsonResult.success("操作成功", operLogListVoList, data.getTotal());
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
        OperLog entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

}