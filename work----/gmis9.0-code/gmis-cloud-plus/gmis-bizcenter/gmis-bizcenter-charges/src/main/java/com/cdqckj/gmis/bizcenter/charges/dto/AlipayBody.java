package com.cdqckj.gmis.bizcenter.charges.dto;

import com.cdqckj.gmis.bizcenter.charges.enumeration.AlipayRequestCodeEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AlipayBody {
    //结果代码
    private String rtnCode = AlipayRequestCodeEnum.SUCCESS.getCode();
    //结果描述
    private String rtnMsg = AlipayRequestCodeEnum.SUCCESS.getDesc();
    //用户编号
    private String consNo = "";
    //用户名称
    private String consName = "";
    //用户地址
    private String addr = "";
    //单位编码
    private String orgNo = "";
    //单位名称
    private String orgName = "";
    //清算单位
    private String acctOrgNo = "";
    //资金编号
    private String capitalNo = "";
    //用户分类
    private String consType = "";
    //账户余额
    private String prepayAmt = "0";
    //合计欠费金额
    private String totalOweAmt = "0";
    //合计应收金额
    private String totalRcvblAmt = "0";
    //合计实收金额
    private String totalRcvedAmt = "0";
    //合计违约金（滞纳金）
    private String totalPenalty = "0";
    //明细记录条数
    private String recordCount = "0";
    //明细记录项目
    private List<RcvblDet> rcvblDet = new ArrayList<>();
    //预留
    private String extend = "";
    /*@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }*/
}
