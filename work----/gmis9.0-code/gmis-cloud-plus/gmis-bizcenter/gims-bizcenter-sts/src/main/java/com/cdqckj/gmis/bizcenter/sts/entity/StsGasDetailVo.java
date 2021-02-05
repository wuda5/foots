package com.cdqckj.gmis.bizcenter.sts.entity;

import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2021/01/21 15:28
 * @remark: 请添加类说明
 */
@Data
public class StsGasDetailVo implements Serializable {

    @ApiModelProperty(value = "普表(用气量)")
    StsDateVo generalGasMeter;

    @ApiModelProperty(value = "卡表表(售气量)")
    StsDateVo cardGasMeter;

    @ApiModelProperty(value = "物联网后付费表(用气量)")
    StsDateVo afterGasMeter;

    @ApiModelProperty(value = "物联网预付费表(售气量)")
    StsDateVo preGasMeter;

    @ApiModelProperty(value = "物联网表端计费表(售气量)")
    StsDateVo lastGasMeter;



}
