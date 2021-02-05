package com.cdqckj.gmis.userarchive.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 销户记录
 * </p>
 *
 * @author gmis
 * @since 2020-12-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "MeterAccountCancelUpdateDTO", description = "销户记录")
public class MeterAccountCancelUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 32, message = "客户名称长度不能超过32")
    private String customerName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasMeterCode;
    /**
     * 卡内金额
     */
    @ApiModelProperty(value = "卡内金额")
    private BigDecimal cardAmount;
    /**
     * 表内金额
     */
    @ApiModelProperty(value = "表内金额")
    private BigDecimal meterAmount;
    /**
     * 表累计气量
     */
    @ApiModelProperty(value = "表累计气量")
    private BigDecimal meterTotalGas;
    /**
     * 未上表金额合计(充值补气和卡上余额)
     */
    @ApiModelProperty(value = "未上表金额合计(充值补气和卡上余额)")
    private BigDecimal rechargeUntometerAmount;
    /**
     * 表是否已恢复出厂
     */
    @ApiModelProperty(value = "表是否已恢复出厂")
    private Boolean isRestoredFactory;
    /**
     * 卡是否回收
     */
    @ApiModelProperty(value = "卡是否回收")
    private Boolean isBackCard;
    /**
     * 表是否回收
     */
    @ApiModelProperty(value = "表是否回收")
    private Boolean isBackMeter;
    /**
     * 销户原因
     */
    @ApiModelProperty(value = "销户原因")
    @Length(max = 500, message = "销户原因长度不能超过500")
    private String reason;
}
