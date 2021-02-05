package com.cdqckj.gmis.common.domain.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 企业
 * </p>
 *
 * @author gmis
 * @since 2019-10-25
 */
@Data
@ApiModel(value = "MulTenant", description = "企业")
public class MulTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业编码")
    private String code;

    @ApiModelProperty(value = "企业名称")
    private String name;

    public MulTenant() {
    }

    public MulTenant(String code) {
        this.code = code;
    }

    public MulTenant(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
