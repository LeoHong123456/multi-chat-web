

package com.javaweb.admin.controller;


import com.javaweb.admin.entity.Article;
import com.javaweb.admin.query.ArticleQuery;
import com.javaweb.admin.service.IArticleService;
import com.javaweb.common.annotation.Log;
import com.javaweb.common.common.BaseController;
import com.javaweb.common.enums.BusinessType;
import com.javaweb.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文章管理 控制器
 * </p>
 *
 * @author admin
 * @since 2020-08-11
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:article:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(ArticleQuery query) {
        return articleService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:article:add")
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody Article entity) {
        return articleService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:article:update")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody Article entity) {
        return articleService.edit(entity);
    }

    /**
     * 获取记录详情
     *
     * @param id    记录ID
     * @param model 模型
     * @return
     */
    @Override
    public String edit(Integer id, Model model) {
        Map<String, Object> info = new HashMap<>();
        if (id != null && id > 0) {
            info = articleService.info(id);
        }
        model.addAttribute("info", info);
        return super.edit(id, model);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @RequiresPermissions("sys:article:delete")
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return articleService.deleteById(id);
    }
	
	/**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @RequiresPermissions("sys:article:dall")
    @Log(title = "文章管理", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return articleService.deleteByIds(ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:article:status")
    @Log(title = "文章管理", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Article entity) {
        return articleService.setStatus(entity);
    }
}
