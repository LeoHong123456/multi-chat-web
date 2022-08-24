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

package com.javaweb.admin.controller;


import com.javaweb.admin.dto.DeleteSkuDto;
import com.javaweb.admin.dto.GenerateSkuDto;
import com.javaweb.admin.dto.UpdateSkuDto;
import com.javaweb.admin.entity.Product;
import com.javaweb.admin.entity.ProductAttributeValue;
import com.javaweb.admin.entity.ProductSku;
import com.javaweb.admin.query.ProductQuery;
import com.javaweb.admin.service.IProductAttributeValueService;
import com.javaweb.admin.service.IProductService;
import com.javaweb.admin.service.IProductSkuService;
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
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-06-09
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductAttributeValueService productAttributeValueService;
    @Autowired
    private IProductSkuService productSkuService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:product:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(ProductQuery query) {
        return productService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:product:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody Product entity) {
        return productService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:product:update")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody Product entity) {
        return productService.edit(entity);
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
            info = productService.info(id);
        }
        model.addAttribute("info", info);
        // 获取SKU规格属性列表
        List<ProductAttributeValue> productAttributeValueList = productAttributeValueService.getProductAttributeValueByProductId(id);
        model.addAttribute("productAttributeValueList", productAttributeValueList);
        // 获取SKU列表
        List<ProductSku> productSkuList = productSkuService.getProductSkuList(id);
        model.addAttribute("productSkuList", productSkuList);
        return super.edit(id, model);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @RequiresPermissions("sys:product:delete")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return productService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:product:dall")
    @Log(title = "商品", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return productService.deleteByIds(ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:product:status")
    @Log(title = "商品", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Product entity) {
        return productService.setStatus(entity);
    }

    /**
     * 生成SKU列表
     *
     * @param productSkuDto 参数
     * @return
     */
    @RequiresPermissions("sys:product:generateSku")
    @ResponseBody
    @PostMapping("/generateSku")
    public JsonResult generateSku(@RequestBody GenerateSkuDto productSkuDto) {
        return productService.generateSku(productSkuDto);
    }

    /**
     * 更新SKU列表
     *
     * @param updateSkuDto 参数
     * @return
     */
    @RequiresPermissions("sys:product:updateSku")
    @ResponseBody
    @PostMapping("/updateSku")
    public JsonResult updateSku(@RequestBody UpdateSkuDto updateSkuDto) {
        return productService.updateSku(updateSkuDto);
    }

    /**
     * 删除SKU列表
     *
     * @param deleteSkuDto 参数
     * @return
     */
    @RequiresPermissions("sys:product:deleteSku")
    @ResponseBody
    @PostMapping("/deleteSku")
    public JsonResult deleteSku(@RequestBody DeleteSkuDto deleteSkuDto) {
        return productService.deleteSku(deleteSkuDto);
    }
}
