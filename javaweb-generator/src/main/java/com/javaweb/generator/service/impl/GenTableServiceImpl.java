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

package com.javaweb.generator.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.generator.constant.GenConstants;
import com.javaweb.generator.entity.GenTable;
import com.javaweb.generator.entity.GenTableColumn;
import com.javaweb.generator.mapper.GenTableColumnMapper;
import com.javaweb.generator.mapper.GenTableMapper;
import com.javaweb.generator.query.GenTableQuery;
import com.javaweb.generator.service.IGenTableService;
import com.javaweb.generator.utils.CodeGenerateUtils;
import com.javaweb.generator.utils.GenUtils;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.generator.vo.GenTableListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代码生成 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-10
 */
@Service
public class GenTableServiceImpl extends BaseServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private CodeGenerateUtils codeGenerateUtils;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        GenTableQuery genTableQuery = (GenTableQuery) query;
        // 查询条件
        QueryWrapper<GenTable> queryWrapper = new QueryWrapper<>();
        // 数据表名
        if (!StringUtils.isEmpty(genTableQuery.getTableName())) {
            queryWrapper.like("table_name", genTableQuery.getTableName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<GenTable> page = new Page<>(genTableQuery.getPage(), genTableQuery.getLimit());
        IPage<GenTable> data = genTableMapper.selectPage(page, queryWrapper);
        List<GenTable> genTableList = data.getRecords();
        List<GenTableListVo> genTableListVoList = new ArrayList<>();
        if (!genTableList.isEmpty()) {
            genTableList.forEach(item -> {
                GenTableListVo genTableListVo = new GenTableListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, genTableListVo);
                // 添加人名称
                if (genTableListVo.getCreateUser() != null && genTableListVo.getCreateUser() > 0) {
                    genTableListVo.setCreateUserName(UserUtils.getName((genTableListVo.getCreateUser())));
                }
                // 更新人名称
                if (genTableListVo.getUpdateUser() != null && genTableListVo.getUpdateUser() > 0) {
                    genTableListVo.setUpdateUserName(UserUtils.getName((genTableListVo.getUpdateUser())));
                }
                genTableListVoList.add(genTableListVo);
            });
        }
        return JsonResult.success("操作成功", genTableListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        GenTable entity = (GenTable) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(GenTable entity) {
        return super.edit(entity);
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
        GenTable entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        Integer result = genTableMapper.deleteById(id);
        if (result == 0) {
            return JsonResult.error();
        }
        // 删除附表信息
        Integer result2 = genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumn>()
                .eq(GenTableColumn::getTableId, id));
        if (result2 == 0) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 批量删除
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
        // 计数器
        Integer totalNum = 0;
        String[] strings = ids.split(",");
        for (String string : strings) {
            GenTable entity = this.getById(string);
            if (entity == null) {
                continue;
            }
            Integer result = genTableMapper.deleteById(string);
            if (result == 0) {
                continue;
            }
            // 删除附表信息
            Integer result2 = genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumn>()
                    .eq(GenTableColumn::getTableId, string));
            if (result2 == 0) {
                continue;
            }
            totalNum++;
        }
        return JsonResult.success(String.format("本次共删除表【%s】张", totalNum));
    }

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult genDbTableList(GenTableQuery query) {
        List<GenTable> genTableList = genTableMapper.genDbTableList(query);
        return JsonResult.success("操作成功", genTableList);
    }

    /**
     * 查询数据库列表
     *
     * @param tableNames 表数组
     * @return
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Transactional
    @Override
    public void importGenTable(List<GenTable> tableList) {
        String operName = ShiroUtils.getUserInfo().getRealname();
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            } catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    /**
     * 获取业务表信息
     *
     * @param id 业务ID
     * @return
     */
    @Override
    public GenTable selectGenTableById(Integer id) {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }

    /**
     * 生成代码
     *
     * @param tableNames 数据表
     * @return
     */
    @Override
    public JsonResult generatorCode(String[] tableNames) {
        Integer totalNum = 0;
        for (String tableName : tableNames) {
            // 查询表信息
            GenTable tableInfo = genTableMapper.selectGenTableByName(tableName);
            try {
                // 生成文件
                codeGenerateUtils.setAuthor(tableInfo.getFunctionAuthor());
                codeGenerateUtils.setAutoRemovePre(true);
                codeGenerateUtils.setPackageName(tableInfo.getPackageName());
                codeGenerateUtils.setModuleName(tableInfo.getModuleName());
                codeGenerateUtils.setTablePredix(tableInfo.getTablePrefix());
                codeGenerateUtils.generateFile(tableInfo.getTableName(), tableInfo.getFunctionName());
                totalNum++;
            } catch (Exception e) {

            }
        }
        return JsonResult.success(String.format("本地共生成【%s】个模块", totalNum));
    }
}