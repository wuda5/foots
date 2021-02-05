package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 开票商品明细项
 *
 * @author ASUS
 */
@Data
@Builder
public class KpInvoiceItem {
    /**
     * 是	1	发票行性质 0 正常行、1 折扣行、2 被 折扣行。
     */
    String type;
    /**
     * 否	50	商品编码。
     */
    String code;
    /**
     * 是	90	商品名称。可在每一行商品下加入折扣行,折扣行商品名称与被折扣商品名称一致,金额和税额栏以负数填写,税率与被折扣行商品税率相同,其它栏不填写。
     */
    String name;
    /**
     * 否	40	规格型号。
     */
    String spec;
    /**
     * 两者要么都为空，要么都不为空。	18(整)6(小)	商品单价。必须等于amount/quantity的四舍五入值。
     */
    BigDecimal price;
    /**
         * 两者要么都为空，要么都不为空。	18(整)6(小)	数量。必须大于等于0.000001。
     */
    BigDecimal quantity;
    /**
     * 否	20	单位。
     */
    String uom;
    /**
     * 是	6(小)	税率。只能为0、 0.03、0.04、0.06、0.10、0.11、0.16、0.17。
     */
    BigDecimal taxRate;
    /**
     * 是	18(整)2(小)	税价合计金额。
     */
    BigDecimal amount;
    /**
     * 是	50	商品分类编码。目前的分类编码为19位，不足19位的在后面补0。
     */
    String catalogCode;
    /**
     * 否	1	优惠政策标识。0:不使用,1:使用。
     */
    String preferentialPolicyFlg;
    /**
     * 否	50	增值税特殊管理（当优惠政策标识为1时必填）。
     */
    String addedValueTaxFlg;
    /**
     * 否	1	零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1:免税。
     */
    String zeroTaxRateFlg;


}
