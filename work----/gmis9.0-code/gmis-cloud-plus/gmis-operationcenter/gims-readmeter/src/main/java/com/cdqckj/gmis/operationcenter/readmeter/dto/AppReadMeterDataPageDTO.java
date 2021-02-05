package com.cdqckj.gmis.operationcenter.readmeter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 实体类
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Validated
@ApiModel(value = "AppReadMeterDataPageDTO", description = "抄表数据")
public class AppReadMeterDataPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;


    /**
     * 计划id
     */
    @ApiModelProperty(value = "计划id,对应数据库字段 plan_id")
    @NotNull(message = "需要传入计划id")
    private Long readMeterPlanId;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @NotNull(message = "需要传入抄表员id")
    private Long readMeterUserId;

    /**
     * 抄表是否完成状态,data_status
     */
    @ApiModelProperty(value = "抄表是否完成状态 数据库字段 data_status,（-1：未抄表；0-已抄")
    private Integer readMeterStatus;


}
