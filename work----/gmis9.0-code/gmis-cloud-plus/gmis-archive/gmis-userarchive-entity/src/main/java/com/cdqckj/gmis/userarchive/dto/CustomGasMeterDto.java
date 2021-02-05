package com.cdqckj.gmis.userarchive.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomGasMeterDto", description = "客户列表和标距")
public class CustomGasMeterDto implements Serializable {
    private IPage<CustomerDesDTO> page ;
    private GasMeter gasMeter;

}
