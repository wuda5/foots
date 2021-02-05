package com.cdqckj.gmis.bizcenter.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 票据打印记录
 * </p>
 *
 * @author gmis
 * @since 2020-10-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReceiptPrintRecordSaveDTO", description = "票据打印记录")
public class ReceiptPrintDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    private String chargeNo;
    /**
     * 打印日期
     */
    @ApiModelProperty(value = "打印日期")
    private LocalDate printDate;
    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Long operatorId;
    /**
     * 操作人姓名
     */
    @ApiModelProperty(value = "操作人姓名")
    @Length(max = 100, message = "操作人姓名长度不能超过100")
    private String operatorName;


}
