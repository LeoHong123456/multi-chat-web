

package com.javaweb.system.service;

import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.dto.LevelDto;
import com.javaweb.system.entity.Level;
import com.javaweb.common.common.IBaseService;
import com.javaweb.system.query.LevelQuery;
import com.javaweb.system.vo.level.LevelListVo;

import java.util.List;

/**
 * <p>
 * 职级 服务类
 * </p>
 *
 * @author admin
 * @since 2020-04-20
 */
public interface ILevelService extends IBaseService<Level> {

    /**
     * 批量设置状态
     *
     * @param levelDto 状态Dto
     * @return
     */
    JsonResult batchStatus(LevelDto levelDto);

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    List<LevelListVo> exportExcel(LevelQuery levelQuery);

}