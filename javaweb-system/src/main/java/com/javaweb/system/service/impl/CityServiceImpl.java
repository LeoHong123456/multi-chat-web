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
import com.javaweb.system.constant.CityConstant;
import com.javaweb.system.entity.City;
import com.javaweb.system.mapper.CityMapper;
import com.javaweb.system.query.CityQuery;
import com.javaweb.system.service.ICityService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.utils.UserUtils;
import com.javaweb.system.vo.city.CityListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 城市 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-05-03
 */
@Service
public class CityServiceImpl extends BaseServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        CityQuery cityQuery = (CityQuery) query;
        // 查询条件
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(cityQuery.getPid()) && cityQuery.getPid() > 0) {
            queryWrapper.eq("pid", cityQuery.getPid());
        } else {
            queryWrapper.eq("pid", 0);
        }
        // 城市名称
        if (!StringUtils.isEmpty(cityQuery.getName())) {
            queryWrapper.like("name", cityQuery.getName());
        }
        // 城市级别：1省份 2市区 3区县
        if (cityQuery.getLevel() != null) {
            queryWrapper.eq("level", cityQuery.getLevel());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("id");

        // 查询数据
        IPage<City> page = new Page<>(cityQuery.getPage(), cityQuery.getLimit());
        IPage<City> data = cityMapper.selectPage(page, queryWrapper);
        List<City> cityList = data.getRecords();
        List<CityListVo> cityListVoList = new ArrayList<>();
        if (!cityList.isEmpty()) {
            cityList.forEach(item -> {
                CityListVo cityListVo = new CityListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, cityListVo);
                // 城市级别描述
                if (cityListVo.getLevel() != null && cityListVo.getLevel() > 0) {
                    cityListVo.setLevelName(CityConstant.CITY_LEVEL_LIST.get(cityListVo.getLevel()));
                }
                // 添加人名称
                if (cityListVo.getCreateUser() > 0) {
                    cityListVo.setCreateUserName(UserUtils.getName((cityListVo.getCreateUser())));
                }
                // 更新人名称
                if (cityListVo.getUpdateUser() > 0) {
                    cityListVo.setUpdateUserName(UserUtils.getName((cityListVo.getUpdateUser())));
                }
                // 是否有子级
                if (item.getLevel() < 3) {
                    cityListVo.setHaveChild(true);
                }
                cityListVoList.add(cityListVo);
            });
        }
        return JsonResult.success("操作成功", cityListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        City entity = (City) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(City entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
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
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        City entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 根据父级城市ID获取子级城市列表
     *
     * @param pid 父级ID
     * @return
     */
    @Override
    public JsonResult getCityListByPid(Integer pid) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("mark", 1);
        List<City> cityList = cityMapper.selectList(queryWrapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!cityList.isEmpty()) {
            cityList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("name", item.getName());
                mapList.add(map);
            });
        }
        return JsonResult.success("操作成功", mapList);
    }

    /**
     * 根据城市ID获取城市名称
     *
     * @param cityId    城市ID
     * @param delimiter 拼接字符
     * @return
     */
    @Override
    public String getCityNameByCityId(Integer cityId, String delimiter) {
        List<String> nameList = new ArrayList<>();
        while (cityId > 1) {
            City cityInfo = cityMapper.selectById(cityId);
            if (cityInfo != null) {
                nameList.add(cityInfo.getName());
                cityId = cityInfo.getPid();
            } else {
                cityId = 1;
            }
        }
        // 使用集合工具实现数组翻转
        Collections.reverse(nameList);
        return org.apache.commons.lang3.StringUtils.join(nameList.toArray(), delimiter);
    }

}