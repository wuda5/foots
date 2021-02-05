package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2021/01/14 11:07
 * @remark: 请添加类说明
 */
@Data
@ApiModel(value = "StsTypeVo", description = "统计的类型")
public class StsTypeVo {

    @ApiModelProperty(value = "id")
    private String typeId;

    @ApiModelProperty(value = "名字")
    private String typeName;

    public StsTypeVo(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
