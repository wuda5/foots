package com.cdqckj.gmis.bizcenter.charges.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AlipayParam {
    /**
     * 清算单位
     */
    private String acctOrgNo;
    /**
     * 查询条件的类别：0－按用户编号查询，1－按手机号码查询，2－按身份证号码查询，3－按原户号查询；（默认值：0）
     */
    private String queryType;
    /**
     * 对应查询类别的查询条件的值
     */
    private String queryValue;
    /**
     * 开始年月（可以缺省，目前暂无用）
     */
    private String bgnYm;
    /**
     * 结束年月（可以缺省，目前暂无用）
     */
    private String endYm;
    /**
     * 费用类型，默认值：11,表示水电燃，热力等主营费用
     */
    private String busiType;
    /**
     * 预留
     */
    private String extend;
    /**
     * 缴费流水号，由支付宝生成，对账维度之一
     */
    private String bankSerial;
    /**
     * 缴费日期，支付宝传入，对账维度之一
     */
    private String bankDate;
    /**
     * 资金编号，取值于欠费查询接口出参。
     */
    private String capitalNo;
    /**
     * 缴费方式，用于区分不同的支付宝缴费（如普通代收，实时代扣等）
     */
    private String payMode;
    /**
     * 交易金额
     */
    private String rcvAmt;
    /**
     * 精确时间，预留
     */
    private String bankDateTime;
    /**
     * 记录数
     */
    private String chargeCnt;
    /**
     * rcvDet明细记录项目
     */
    private String rcvDet;

    private List<RcvblDet> rcvDetList;
}
