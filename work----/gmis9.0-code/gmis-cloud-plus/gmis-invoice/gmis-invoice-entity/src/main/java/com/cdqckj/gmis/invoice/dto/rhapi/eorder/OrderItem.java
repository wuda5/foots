package com.cdqckj.gmis.invoice.dto.rhapi.eorder;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细请求参数
 * @author ASUS
 */
@Data
public class OrderItem {
    /**
     * 否	50	商品编码。
     */
    private String code;
    /**
     * 是	90	商品名称。
     */
    private String name;
    /**
     * 否	40	规格型号。
     */
    private String spec;
    /**
     * 否	18(整)6(小)	商品单价。必须等于金额/数量的四舍五入值。（price和quantity两者都为空或都不为空）
     */
    private BigDecimal price;
    /**
     * 否	18(整)6(小)	数量。必须大于等于0.000001。（price和quantity两者都为空或都不为空）（成品油企业数量必填）
     */
    private BigDecimal quantity;
    /**
     * 否	20	单位。（成品油行业只允许开“吨”或者“升”）
     */
    private String uom;
    /**
     * 是	2(小)	税率。只能为0、0.03、0.04、0.06、0.10或0.16。
     */
    private BigDecimal taxRate;
    /**
     * 是	18(整)6(小)	税价合计金额。
     */
    private BigDecimal amount;
    /**
     * 否	18(整)6(小)	折扣金额，金额为正数
     */
    private BigDecimal discountAmount;
    /**
     * 否	50	商品分类编码。如果为空，必须在瑞宏网后台手动配置商品名称与分类编码表，开票时会从该表中获取分类编码。如果不为空，则优先使用传入的值。目前的分类编码为19位，不足19位的在后面补0。
     */
    private String catalogCode;
    /**
     * 否	1	优惠政策标识。0:不使用,1:使用。
     */
    private String preferentialPolicyFlg;
    /**
     * 当优惠政策标识为1时必填	1	增值税特殊管理。
     */
    private String addedValueTaxFlg;
    /**
     * 否	1	零税率标识。1:免税,2:不征税,3:普通零税率。税率为零的情况下，如果不传，则默认为1。
     */
    private String zeroTaxRateFlg;


}
