package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 响应发票信息的项目明细
 * @author ASUS
 */
@Data
public class ResultInvoiceItem {
    /**
     * 发票行性质 0 正常行、1 折扣行、2 被折扣行。
     */
    private String type;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 商品名称 带商品税收分类编码简称，
     * 例如：开票时传入商品名称为“水费”，则这里会返回“水冰雪水费”，详见《国家税务总局公告2017年第45号》第一条。
     */
    private String name;
    /**
     * 规格型号
     */
    private String spec;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal quantity;
    /**
     * 单位
     */
    private String uom;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 税价合计金额
     */
    private BigDecimal amount;
    /**
     * 不含税金额
     */
    private BigDecimal noTaxAmount;
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * 商品分类编码
     */
    private String catalogCode;
    /**
     * 优惠政策标识
     */
    private String preferentialPolicyFlg;
    /**
     * 增值税特殊管理
     */
    private String addedValueTaxFlg;
    /**
     * 零税率标识
     */
    private String zeroTaxRateFlg;
}