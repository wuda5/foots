package com.cdqckj.gmis.authority.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/16 15:37
 * @remark: 请输入类说明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ResetTenantMRDTO", description = "重新设置一个租户的菜单和资源")
public class ResetTenantMRDTO {

    @ApiModelProperty(value = "租户的code")
    @NotEmpty
    private String tenantCode;

    @ApiModelProperty(value = "保留菜单的Id")
    @NotNull
    private List<Long> menuIdList = new ArrayList<>();

    @ApiModelProperty(value = "保留资源的Id")
    @NotNull
    private List<Long> resourceIdList = new ArrayList<>();

}
