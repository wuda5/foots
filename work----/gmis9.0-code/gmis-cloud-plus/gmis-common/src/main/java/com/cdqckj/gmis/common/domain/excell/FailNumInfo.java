package com.cdqckj.gmis.common.domain.excell;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/12/15 20:00
 * @remark: 验证错误的信息和编号
 */
@Data
public class FailNumInfo {

    @ApiModelProperty(value = "第几个验证方案(包括行和列的验证),从0开始")
    private Integer validNo;

    @ApiModelProperty(value = "错误的描述")
    private String failDescribe;

    public FailNumInfo(Integer validNo, String failDescribe) {
        this.validNo = validNo;
        this.failDescribe = failDescribe;
    }
}
