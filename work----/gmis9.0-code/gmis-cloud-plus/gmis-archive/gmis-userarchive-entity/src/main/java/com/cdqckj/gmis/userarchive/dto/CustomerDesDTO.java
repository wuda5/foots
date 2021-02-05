package com.cdqckj.gmis.userarchive.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerDesDTO", description = "")
public class CustomerDesDTO  implements Serializable {

    @ApiModelProperty(value = "黑名单状态")
    private Integer Blackliststatus;
    @ApiModelProperty(value = "客户参数")
    private IPage<Customer> customer;

}
