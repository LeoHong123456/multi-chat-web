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

package com.javaweb.generator.mapper;

import com.javaweb.generator.entity.GenTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.generator.query.GenTableQuery;

import java.util.List;

/**
 * <p>
 * 代码生成 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-10
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    List<GenTable> genDbTableList(GenTableQuery query);

    /**
     * 根据表明获取数据库列表
     *
     * @param tableNames 数据库名
     * @return
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 插入数据表
     *
     * @param genTable 待生成数据表
     * @return
     */
    int insertGenTable(GenTable genTable);

    /**
     * 根据表名查询业务表
     *
     * @param tableName 表名
     * @return
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * 根据业务ID获取表信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Integer id);

}
