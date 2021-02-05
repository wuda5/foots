package com.cdqckj.gmis.bizcenter.charges.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class RcvblDet {
    //应收标识
    private String rcvblAmtId;
    //用户户号
    private String consNo;
    //户名
    private String consName;
    //单位编码
    private String orgNo;
    //单位名称
    private String orgName;
    //清算单位
    private String acctOrgNo;
    //应收年月
    private String rcvblYm;
    //电量、水量（吨）等：用于计费的计量结果
    private String tPq;
    //应收金费
    private String rcvblAmt = "0";
    //实收金费
    private String rcvedAmt = "0";
    //应收违约金（滞纳金）
    private String rcvblPenalty = "0";
    //欠费小计
    private String oweAmt = "0";
    //预留
    private String extend = "";
    //预留
    private String extendDet = "";

    @Builder
    public RcvblDet(String consNo, String rcvblAmtId, String rcvblYm){
        this.consNo = consNo;
        this.rcvblAmtId = rcvblAmtId;
        this.rcvblYm = rcvblYm;
    }
}
