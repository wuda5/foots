package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 柜台综合：过户业务表，业务状态未完成之前不会更改主表数据
 * </p>
 *
 * @author gmis
 * @since 2020-11-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasmeterTransferAccountSaveDTO", description = "柜台综合：过户业务表，业务状态未完成之前不会更改主表数据")
public class GasmeterTransferAccountSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 32, message = "表具编号长度不能超过32")
    private String gasMeterCode;
    /**
     * 原客户编号
     */
    @ApiModelProperty(value = "原客户编号")
    @Length(max = 32, message = "原客户编号长度不能超过32")
    private String oldCustomerCode;

    /**
     * 新客户code
     */
    @ApiModelProperty(value = "新客户code")
    private String customerCode;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    private String customerChargeNo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark;
    /**
     * 过户业务状态：0’取消；1‘完成；2’待缴费；3‘处理中
     */
    @ApiModelProperty(value = "过户业务状态：0’取消；1‘完成；2’待缴费；3‘处理中")
    private Integer status;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

}
