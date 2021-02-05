package com.cdqckj.gmis.iot.qc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-10-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotGasMeterDayDataSaveDTO", description = "")
public class IotGasMeterDayDataSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    private String gasMeterCode;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    private String versionName;

    /**
     * 气表余额
     */
    @ApiModelProperty(value = "气表余额")
    private BigDecimal gasMeterBalance;

    /**
     * 累积用气量
     */
    @ApiModelProperty(value = "累积用气量")
    private BigDecimal totalUseGas;

    /**
     * 近一天用气量
     */
    @ApiModelProperty(value = "近一天用气量")
    private BigDecimal dayUse1;

    /**
     * 近2天用气量
     */
    @ApiModelProperty(value = "近2天用气量")
    private BigDecimal dayUse2;

    /**
     * 近3天用气量
     */
    @ApiModelProperty(value = "近3天用气量")
    private BigDecimal dayUse3;

    /**
     * 近4天用气量
     */
    @ApiModelProperty(value = "近4天用气量")
    private BigDecimal dayUse4;

    /**
     * 近5天用气量
     */
    @ApiModelProperty(value = "近5天用气量")
    private BigDecimal dayUse5;

    /**
     * 阀门状态
     */
    @ApiModelProperty(value = "阀门状态")
    private Integer valveState;

    /**
     * 表类型(0-表端,1-中心计费)
     */
    @ApiModelProperty(value = "表类型(0-表端,1-中心计费)")
    private Integer meterType;

    /**
     * 冻结时间
     */
    @ApiModelProperty(value = "冻结时间")
    private LocalDateTime freezingTime;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    private String installAddress;

    /**
     * 客户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String customerPhone;
}
