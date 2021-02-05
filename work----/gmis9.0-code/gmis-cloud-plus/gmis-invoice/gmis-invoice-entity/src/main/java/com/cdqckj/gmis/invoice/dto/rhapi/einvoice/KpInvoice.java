package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 瑞宏网电子发票：开票主体数据
 *
 * @author ASUS
 */
@Data
@Builder
public class KpInvoice {
    /**
     * 是	20	销货方纳税人识别号。
     */
    String taxpayerCode;
    /**
     * 否	100	销货方纳税人名称
     */
    String taxpayerName;
    /**
     * 否	79	销货方地址。如果填写则使用填写的信息，否则使用纳税人注册时预留的信息。
     */
    String taxpayerAddress;
    /**
     * 否	20	销货方电话。
     */
    String taxpayerTel;
    /**
     * 否	69	销货方开户银行。
     */
    String taxpayerBankName;
    /**
     * 否	30	销货方银行账号。
     */
    String taxpayerBankAccount;
    /**
     * 是	100	购货方名称，即发票抬头。
     */
    String customerName;
    /**
     * 否	20	购货方纳税人识别号或者个人身份证号
     */
    String customerCode;
    /**
     * 否	79	购货方地址
     */
    String customerAddress;
    /**
     * 否	20	购货方电话。
     */
    String customerTel;
    /**
     * 否	69	购货方开户银行。
     */
    String customerBankName;
    /**
     * 否	30	购货方银行账号。
     */
    String customerBankAccount;
    /**
     * 否	2	开票类型。p:电子增值税普通发票（默认） ps:电子收购发票 py：成品油
     */
    String invoiceType;
    /**
     * 否	50	店铺编号
     */
    String shopCode;
    /**
     * 否	50	店铺名称
     */
    String shopName;
    /**
     * 否	50	支付方式
     */
    String payType;
    /**
     * 否	50	支付流水号
     */
    String payBillNo;
    /**
     * 是	8	开票人
     */
    String drawer;
    /**
     * 否	8	收款人。
     */
    String payee;
    /**
     * 否	8	复核人。
     */
    String reviewer;
    /**
     * 是	18(整)2(小)	税价合计金额。必须大于等于0.01元；必须等于发票明细合计金额；必须小于等于在税务局进行票种核定时确定的单张发票开票限额。
     */
    BigDecimal totalAmount;
    /**
     * 否	130	发票备注。
     */
    String remark;

    /**
     * 商品详情
     */
    List<KpInvoiceItem> items;


}
