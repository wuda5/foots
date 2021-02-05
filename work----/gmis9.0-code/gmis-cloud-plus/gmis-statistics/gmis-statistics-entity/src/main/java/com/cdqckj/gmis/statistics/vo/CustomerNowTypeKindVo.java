package com.cdqckj.gmis.statistics.vo;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/17 14:30
 * @remark: 客户增量统计
 */
@Data
@ApiModel(value = "CustomerNowTypeKindVo", description = "客户增量统计")
public class CustomerNowTypeKindVo implements Serializable {

    @ApiModelProperty(value = "开户")
    private List<StsInfoBaseVo<String, Long>> openAccountSts;

    @ApiModelProperty(value = "销户")
    private List<StsInfoBaseVo<String, Long>> closeAccountSts;
}
