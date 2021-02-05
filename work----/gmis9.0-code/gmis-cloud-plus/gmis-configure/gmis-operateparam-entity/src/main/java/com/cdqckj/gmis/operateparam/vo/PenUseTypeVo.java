package com.cdqckj.gmis.operateparam.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
//@Builder
@ApiModel(value = "PenUseTypeVo", description = "")
public class PenUseTypeVo extends Penalty implements Serializable {
    /**
     * 用气类型ID
     */

    @ApiModelProperty(value = "用气类型名称")
    private String useGasTypeName;


    @ApiModelProperty(value = "利率展示String")
    private String rateTwo;
}
