package com.cdqckj.gmis.common.domain.sts;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/11/17 13:18
 * @remark: 统计的基本信息
 */
@Data
@ApiModel(value = "StsInfoBaseVo", description = "统计的基本信息")
public class StsInfoBaseVo<T,A> implements Serializable, SeparateKey {

    @ApiModelProperty(value = "类型")
    private T type;

    @ApiModelProperty(value = "名字")
    private String typeName;

    @ApiModelProperty(value = "数量/个数/总数")
    private A amount;

    @Override
    public String indexKey() {
        return String.valueOf(type);
    }
}
