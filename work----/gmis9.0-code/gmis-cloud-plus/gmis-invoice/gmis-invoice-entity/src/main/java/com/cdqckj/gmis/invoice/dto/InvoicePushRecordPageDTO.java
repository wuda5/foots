package com.cdqckj.gmis.invoice.dto;

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
 * 电子发票推送记录表
 * </p>
 *
 * @author gmis
 * @since 2020-11-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoicePushRecordPageDTO", description = "电子发票推送记录表")
public class InvoicePushRecordPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
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
     * 推送的平台
     */
    @ApiModelProperty(value = "推送的平台")
    @Length(max = 50, message = "推送的平台长度不能超过50")
    private String pushPlat;
    /**
     * 推送的url
     */
    @ApiModelProperty(value = "推送的url")
    @Length(max = 100, message = "推送的url长度不能超过100")
    private String pushUrl;
    /**
     * 推送的接口执行的操作
     */
    @ApiModelProperty(value = "推送的接口执行的操作")
    @Length(max = 50, message = "推送的接口执行的操作长度不能超过50")
    private String pushCmdName;
    /**
     * 推送的操作流水号
     */
    @ApiModelProperty(value = "推送的操作流水号")
    @Length(max = 50, message = "推送的操作流水号长度不能超过50")
    private String serialNo;
    /**
     * 推送的发票id
     */
    @ApiModelProperty(value = "推送的发票id")
    private Long invoiceId;
    /**
     * 推送的发票编号
     */
    @ApiModelProperty(value = "推送的发票编号")
    @Length(max = 30, message = "推送的发票编号长度不能超过30")
    private String invoiceNumber;
    /**
     * 推送的数据
     */
    @ApiModelProperty(value = "推送的数据")
    @Length(max = 3000, message = "推送的数据长度不能超过3000")
    private String pushData;
    /**
     * 推送日期
     */
    @ApiModelProperty(value = "推送日期")
    private LocalDate pushDate;
    /**
     * 推送结果code
     */
    @ApiModelProperty(value = "推送结果code")
    @Length(max = 10, message = "推送结果code长度不能超过10")
    private String pushResultCode;
    /**
     * 推送结果
     */
    @ApiModelProperty(value = "推送结果")
    @Length(max = 100, message = "推送结果长度不能超过100")
    private String pushResult;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer deleteStatus;

}
