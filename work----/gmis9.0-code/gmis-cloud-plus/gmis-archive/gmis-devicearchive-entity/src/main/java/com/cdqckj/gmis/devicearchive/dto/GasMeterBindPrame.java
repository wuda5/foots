package com.cdqckj.gmis.devicearchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterBindPrame", description = "气表参数信息App用")
public class GasMeterBindPrame {
    /**
     * 客户档案编号
     */
    @ApiModelProperty(value = "客户档案编号")
    @Length(max = 32, message = "客户档案编号长度不能超过32")
    private String customerCode;

    /**
     * 绑定的其他客户的气表档案编号
     */
    @ApiModelProperty(value = "绑定的其他客户的气表档案编号")
    @Length(max = 32, message = "绑定的其他客户的气表档案编号长度不能超过32")
    private String bindGasMeterCode;
}
