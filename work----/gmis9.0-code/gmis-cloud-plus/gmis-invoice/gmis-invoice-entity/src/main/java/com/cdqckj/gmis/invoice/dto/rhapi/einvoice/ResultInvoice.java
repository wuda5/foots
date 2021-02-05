package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 响应发票信息
 * @author ASUS
 */
@Data
public class ResultInvoice implements Serializable {
    /**
     * 发票状态代码
     */
    public interface Status {
        /**
         * 正常
         */
        String NORMAL = "1";
        /**
         * 红冲
         */
        String RED = "3";
        /**
         * 被红冲
         */
        String REDED = "4";
    }

    /**
     * 订单编号。
     */
    private String orderNo;
    /**
     * 销货方名称。
     */
    private String taxpayerName;
    /**
     * 销货方纳税人识别号。
     */
    private String taxpayerCode;
    /**
     * 销货方地址。
     */
    private String taxpayerAddress;
    /**
     * 销货方电话。
     */
    private String taxpayerTel;
    /**
     * 销货方开户银行。
     */
    private String taxpayerBankName;
    /**
     * 销货方银行账号。
     */
    private String taxpayerBankAccount;
    /**
     * 购货方名称。
     */
    private String customerName;
    /**
     * 购货方纳税人识别号。
     */
    private String customerCode;
    /**
     * 购货方地址。
     */
    private String customerAddress;
    /**
     * 购货方电话。
     */
    private String customerTel;
    /**
     * 购货方开户银行。
     */
    private String customerBankName;
    /**
     * 购货方银行账号。
     */
    private String customerBankAccount;
    /**
     * 发票代码＋发票号码。
     */
    private String code;
    /**
     * 校验码。
     */
    private String checkCode;
    /**
     * 税控码。
     */
    private String fiscalCode;
    /**
     * 发票状态 1:正常 3:红冲 4:被红冲。
     */
    private String status;
    /**
     * 开票日期 格式为yyyy-MM-dd HH:mm:ss（蓝字发票、红字发票发票开具的时间）。
     */
    private String generateTime;
    /**
     * 税价合计金额。
     */
    private BigDecimal totalAmount;
    /**
     * 不含税金额。
     */
    private BigDecimal noTaxAmount;
    /**
     * 税额。
     */
    private BigDecimal taxAmount;
    /**
     * 开票人。
     */
    private String drawer;
    /**
     * 收款人。
     */
    private String payee;
    /**
     * 复核人。
     */
    private String reviewer;
    /**
     * 发票备注。
     */
    private String remark;
    /**
     * PDF文件下载地址。
     */
    private String pdfUnsignedUrl;
    /**
     * 发票查看地址。
     */
    private String viewUrl;
    /**
     * 关联发票代码+号码。如果发票被冲红，则与被冲红发票相互关联。
     */
    private String relatedCode;
    /**
     * 冲红原因。
     */
    private String validReason;
    /**
     * 冲红时间（指红冲发票的税控时间，在红字发票中和上面的generateTime是同一个）。
     */
    private String validTime;
    /**
     * 项目明细
     */
    private List<ResultInvoiceItem> items;
    /**
     * 开票或冲红时传入的扩展参数。
     */
    Map<String, Object> extendedParams;
}
