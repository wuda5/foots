package com.cdqckj.gmis.common.domain.excell.rule;

import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: lijianguo
 * @time: 2020/10/27 13:16
 * @remark: 请输入类说明
 */
public abstract class AbstractValidRule implements ValidRule {

    @ApiModelProperty(value = "验证的列的个数")
    protected int colStart;

    @ApiModelProperty(value = "验证的列的个数")
    protected int colSum;

    @ApiModelProperty(value = "验证的类型")
    protected int type;

    @ApiModelProperty(value = "失败的说明")
    protected String validDesc;

    @ApiModelProperty(value = "空的说明")
    protected String emptyDesc;
}
