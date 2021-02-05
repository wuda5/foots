package com.cdqckj.gmis.biztemporary.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 气表拆表记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "RemoveMeterRecordUpdateDTO", description = "气表拆表记录")
public class RemoveMeterRecordUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号", hidden = true)
    @Length(max = 32, message = "公司编号长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", hidden = true)
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID", hidden = true)
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称", hidden = true)
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID", hidden = true)
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称", hidden = true)
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号", hidden = true)
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称", hidden = true)
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 拆表记录编号
     */
    @ApiModelProperty(value = "拆表记录编号", hidden = true)
    @Length(max = 50, message = "拆表记录编号长度不能超过50")
    private String removeMeterNo;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 50, message = "缴费编号长度不能超过50")
    private String chargeNo;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String meterCode;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID", hidden = true)
    private Long meterFactoryId;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称", hidden = true)
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String meterFactoryName;
    /**
     * 气表型号id
     */
    @ApiModelProperty(value = "气表型号id", hidden = true)
    private Long meterModelId;
    /**
     * 气表型号名称
     */
    @ApiModelProperty(value = "气表型号名称", hidden = true)
    @Length(max = 30, message = "气表型号名称长度不能超过30")
    private String meterModelName;
    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID", hidden = true)
    private Long meterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称", hidden = true)
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String meterVersionName;
    /**
     * 旧表止数
     */
    @ApiModelProperty(value = "旧表止数")
    private BigDecimal meterEndGas;
    /**
     * 表具余额
     */
    @ApiModelProperty(value = "表具余额")
    private BigDecimal meterBalance;
    /**
     * 生成的抄表数据Id
     */
    @ApiModelProperty(value = "生成的抄表数据Id")
    private Long readMeterId;
    /**
     * 结算产生的欠费记录id
     */
    @ApiModelProperty(value = "结算产生的欠费记录id")
    private Long arrearsDetailId;
    /**
     * 结算产生的多条欠费记录id
     */
    @ApiModelProperty(value = "结算产生的多条欠费记录id")
    @Length(max = 300, message = "结算产生的多条欠费记录id长度不能超过300")
    private String arrearsDetailIdList;
    /**
     * 拆表原因
     */
    @ApiModelProperty(value = "拆表原因", hidden = true)
    @Length(max = 300, message = "拆表原因长度不能超过300")
    private String removeReason;
    /**
     * 拆表日期
     */
    @ApiModelProperty(value = "拆表日期", hidden = true)
    private LocalDate removeDate;
    /**
     * 业务状态：0取消；1完成；2待缴费；3处理中
     */
    @ApiModelProperty(value = "业务状态：0取消；1完成；2待缴费；3处理中", hidden = true)
    private Integer status;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态", hidden = true)
    private Integer deleteStatus;
}
