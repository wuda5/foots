package com.cdqckj.gmis.statistics.vo;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/11/11 14:57
 * @remark: 发票的类型的统计
 */
@Data
@ApiModel(value = "FeiDayPayTypeVo", description = "费用的统计")
public class InvoiceDayKindVo implements Serializable, SeparateKey {

    @ApiModelProperty(value = "发票的数量")
    private Integer amount;

    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "合计税额")
    private BigDecimal totalTax;

    @ApiModelProperty(value = "发票种类")
    private String invoiceKindCode;

    @ApiModelProperty(value = "发票种类的名称")
    private String invoiceKindName;

    @Override
    public String indexKey() {
        return invoiceKindCode;
    }
}
