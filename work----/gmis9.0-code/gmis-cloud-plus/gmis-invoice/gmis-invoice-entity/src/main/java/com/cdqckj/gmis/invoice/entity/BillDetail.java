package com.cdqckj.gmis.invoice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode()
@Accessors(chain = true)
@AllArgsConstructor
public class BillDetail {
    private static final long serialVersionUID = BillDetail.class.hashCode();
    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @TableField(exist = false)
    private Long tollItemId;

    /**
     * 收费项名称
     */
    @ApiModelProperty(value = "收费项名称")
    @TableField(exist = false)
    private String tollItemName;

    /**
     * 气量
     */
    @ApiModelProperty(value = "气量")
    private String gasVolume;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
}
