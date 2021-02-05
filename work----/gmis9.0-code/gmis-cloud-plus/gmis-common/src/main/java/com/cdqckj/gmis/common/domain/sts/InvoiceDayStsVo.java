package com.cdqckj.gmis.common.domain.sts;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/18 14:32
 * @remark: 请添加类说明
 */
@Data
public class InvoiceDayStsVo implements Serializable, SeparateKey {

    @ApiModelProperty(value = "发票的数量")
    private Integer amount = 0;

    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "合计税额")
    private BigDecimal totalTax = BigDecimal.ZERO;

    @ApiModelProperty(value = "种类/类型")
    private String kt;

    @ApiModelProperty(value = "名字")
    private String ktName;

    @ApiModelProperty(value = "名字")
    private List<InvoiceDayStsVo> kindList = new ArrayList<>();

    @Override
    public String indexKey() {
        return kt;
    }

}
