package com.cdqckj.gmis.operationcenter.readmeter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel(value = "AppReadBookDTO", description = "抄表册")
public class AppReadBookDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @NotNull(message = "需要传入抄表员id")
    private Long readMeterUserId;

//    /**
//     * 抄表是否完成状态,data_status
//     */
//    @ApiModelProperty(value = "抄表是否完成状态 数据库字段 data_status,（-1：未抄表；0-已抄")
//    private Integer readMeterStatus;


}
