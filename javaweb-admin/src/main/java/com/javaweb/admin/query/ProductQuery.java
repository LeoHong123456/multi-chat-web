// +----------------------------------------------------------------------
// | JavaWeb_Layui混编版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站:
// +----------------------------------------------------------------------
// | 作者: admin 
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

package com.javaweb.admin.query;

import com.javaweb.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 商品查询条件
 * </p>
 *
 * @author admin
 * @since 2020-06-09
 */
@Data
public class ProductQuery extends BaseQuery {

    /**
     * 是否热卖：1是 2否
     */
    private Integer isHot;

    /**
     * 是否推荐：1是 2否
     */
    private Integer isRecommand;

    /**
     * 是否新品：1是 2否
     */
    private Integer isNew;

    /**
     * 审核状态：1已审核 2待审核
     */
    private Integer verifyStatus;

    /**
     * 上架状态：1已上架 2已下架
     */
    private Integer status;

    /**
     * 产品服务：1无忧退货 2快速退款 3免费包邮
     */
    private Integer service;

    /**
     * 促销类型：0没有促销使用原价 1使用促销价 2使用会员价 3使用阶梯价格 4使用满减价 5限时购
     */
    private Integer promotionType;

    /**
     * 是否包邮：1是 2否
     */
    private Integer isPostage;

}
