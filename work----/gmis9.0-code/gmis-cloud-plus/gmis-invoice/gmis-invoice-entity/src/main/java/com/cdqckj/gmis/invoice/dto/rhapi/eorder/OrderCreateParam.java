package com.cdqckj.gmis.invoice.dto.rhapi.eorder;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 创建订单请求参数
 *
 * @author ASUS
 */
@Data
public class OrderCreateParam {
    /**
     * 是	50	订单编号。
     */
    private String orderNo;
    /**
     * 否	50	子订单编号
     */
    private String subOrderNo;
    /**
     * 否	50	购货方扫码开票时使用的唯一标识。
     */
    private String scanCodeKey;
    /**
     * 是	20	销货方纳税人识别号。
     */
    private String taxpayerCode;
    /**
     * 否	2	开票类型。p:电子增值税普通发票（默认），ps:电子收购发票，c:纸质普通发票，cs:纸质收购票，s:增值税专用发票，py:成品油。
     */
    private String invoiceType;
    /**
     * 否	50	店铺编号
     */
    private String shopCode;
    /**
     * 否	100	店铺名称。
     */
    private String shopName;
    /**
     * 是	19	订单创建时间。格式为yyyy-MM-dd HH:mm:ss。
     */
    private String orderTime;
    /**
     * 否	100	联系人
     */
    private String contact;
    /**
     * 否	100	联系电话
     */
    private String contactTel;
    /**
     * 否	100	联系邮箱
     */
    private String contactMail;
    /**
     * 否	250	配送地址
     */
    private String shippingAddress;
    /**
     * 否	100	销货方名称( 如果为空则使用纳税人注册时预留的信息，不为空则优先使用传入的值)
     */
    private String taxpayerName;
    /**
     * 否	79	销货方地址( 如果为空则使用纳税人注册时预留的信息，不为空则优先使用传入的值)
     */
    private String taxpayerAddress;
    /**
     * 否	20	销货方电话( 如果为空则使用纳税人注册时预留的信息，不为空则优先使用传入的值)
     */
    private String taxpayerTel;
    /**
     * 否	69	销货方开户银行( 如果为空则使用纳税人注册时预留的信息，不为空则优先使用传入的值)
     */
    private String taxpayerBankName;
    /**
     * 否	30	销货方银行账号( 如果为空则使用纳税人注册时预留的信息，不为空则优先使用传入的值)
     */
    private String taxpayerBankAccount;
    /**
     * 是	100	购货方名称，即发票抬头；选择扫码开票方案时，即 scanCodeKey不为空时，为非必填项；
     */
    private String customerName;
    /**
     * 否	20	购货方纳税人识别号。
     */
    private String customerCode;
    /**
     * 否	79	购货方地址。
     */
    private String customerAddress;
    /**
     * 否	20	购货方电话。
     */
    private String customerTel;
    /**
     * 否	69	购货方开户银行。
     */
    private String customerBankName;
    /**
     * 否	30	购货方银行账号。
     */
    private String customerBankAccount;
    /**
     * 否    是否直接开票。true:直接开具发票；false:只保存订单信息，不立即执行开票。默认为false。
     */
    private Boolean autoBilling;
    /**
     * 是	8	开票人。
     */
    private String drawer;
    /**
     * 否	8	收款人。
     */
    private String payee;
    /**
     * 否	8	复核人。
     */
    private String reviewer;
    /**
     * 是	18(整)6(小)	税价合计金额。必须大于等于0.01元；必须等于明细合计金额；必须小于等于在税务局进行票种核定时确定的单张发票开票限额。
     */
    private BigDecimal totalAmount;
    /**
     * 否	130	发票备注。
     */
    private String remark;
    /**
     * 是	100	发票项目明细列表。每张发票最多一百条。
     */
    private List<OrderItem> orderItems;
    /**
     * 否	20	扩展参数。一组Key-Value形式的数据，会在响应报文中回传给调用方，由调用者和瑞宏网双方根据实际情况协商使用。
     */
    private Map<String, Object> extendedParams;
    /**
     * 否    自定义参数。一组Key-Value形式的数据，key值：callbackUrl，Value为回调地址“http://wwww.***.com/test/invoice”
     */
    private Map<String, Object> dynamicParams;
}
