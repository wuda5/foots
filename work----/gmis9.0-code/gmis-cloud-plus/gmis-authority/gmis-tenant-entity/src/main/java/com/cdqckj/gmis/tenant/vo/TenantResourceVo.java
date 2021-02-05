package com.cdqckj.gmis.tenant.vo;

import com.cdqckj.gmis.common.domain.tenant.SetTenantInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/10/8 14:27
 * @remark: 这个租户的和这个资源的关系
 */
@Data
public class TenantResourceVo implements Serializable, SetTenantInfo {

    @ApiModelProperty(value = "租户的名字")
    private String name;

    @ApiModelProperty(value = "租户的code")
    private String code;

    @ApiModelProperty(value = "1拥有 0没有")
    private Boolean holdAuthority;

    @ApiModelProperty(value = "资源的Id")
    private Long resourceId;

    @ApiModelProperty(value = "权限的名称")
    private String resourceName;

    @Override
    public void setTenantCode(String code) {
        this.code = code;
    }

    @Override
    public void setTenantName(String name) {
        this.name = name;

    }
}
