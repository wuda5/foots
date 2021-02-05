package com.cdqckj.gmis.operationcenter.readmeter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

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
@ApiModel(value = "AppCustomerMeterPageDTO", description = "抄表册对应的关联客户表具列表")
public class AppCustomerMeterPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    private Long readMeterBook;
//    /**
//     * 气表编号
//     */
//    @ApiModelProperty(value = "气表编号")
//    private String gasMeterCode;

    @ApiModelProperty(value = "页面大小", example = "10")
    private long size = 10;

    @ApiModelProperty(value = "当前页", example = "1")
    private long current = 1;


//    /**
//     * 抄表员id
//     */
//    @ApiModelProperty(value = "抄表员id")
//    @NotNull(message = "需要传入抄表员id")
//    private Long readMeterUserId;



}
