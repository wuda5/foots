package com.cdqckj.gmis.iot.qc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;


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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("qw_iot_gas_meter_day_data")
@ApiModel(value = "IotGasMeterDayData", description = "")
@AllArgsConstructor
public class IotGasMeterDayData extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 32, message = "表身号不能超过50")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @TableField(value = "version_name", condition = LIKE)
    @Excel(name = "版号名称")
    private String versionName;

    /**
     * 气表余额
     */
    @ApiModelProperty(value = "气表余额")
    @TableField("gas_meter_balance")
    @Excel(name = "气表余额")
    private BigDecimal gasMeterBalance;

    /**
     * 累积用气量
     */
    @ApiModelProperty(value = "累积用气量")
    @TableField("total_use_gas")
    @Excel(name = "累积用气量")
    private BigDecimal totalUseGas;

    /**
     * 近一天用气量
     */
    @ApiModelProperty(value = "近一天用气量")
    @TableField("day_use_1")
    @Excel(name = "近一天用气量")
    private BigDecimal dayUse1;

    /**
     * 近2天用气量
     */
    @ApiModelProperty(value = "近2天用气量")
    @TableField("day_use_2")
    @Excel(name = "近2天用气量")
    private BigDecimal dayUse2;

    /**
     * 近3天用气量
     */
    @ApiModelProperty(value = "近3天用气量")
    @TableField("day_use_3")
    @Excel(name = "近3天用气量")
    private BigDecimal dayUse3;

    /**
     * 近4天用气量
     */
    @ApiModelProperty(value = "近4天用气量")
    @TableField("day_use_4")
    @Excel(name = "近4天用气量")
    private BigDecimal dayUse4;

    /**
     * 近5天用气量
     */
    @ApiModelProperty(value = "近5天用气量")
    @TableField("day_use_5")
    @Excel(name = "近5天用气量")
    private BigDecimal dayUse5;

    /**
     * 阀门状态
     */
    @ApiModelProperty(value = "阀门状态")
    @TableField("valve_state")
    @Excel(name = "阀门状态")
    private Integer valveState;

    /**
     * 表类型(0-表端,1-中心计费)
     */
    @ApiModelProperty(value = "表类型(0-表端,1-中心计费)")
    @TableField("meter_type")
    @Excel(name = "表类型(0-表端,1-中心计费)")
    private Integer meterType;

    /**
     * 冻结时间
     */
    @ApiModelProperty(value = "冻结时间")
    @TableField("freezing_time")
    @Excel(name = "冻结时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime freezingTime;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @TableField(value = "install_address", condition = LIKE)
    @Excel(name = "安装地址")
    private String installAddress;

    /**
     * 客户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户姓名")
    private String customerName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @TableField(value = "customer_phone", condition = LIKE)
    @Excel(name = "联系电话")
    private String customerPhone;


    @Builder
    public IotGasMeterDayData(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String gasMeterNumber,String gasMeterCode,String versionName,
                    BigDecimal gasMeterBalance, BigDecimal totalUseGas, BigDecimal dayUse1, BigDecimal dayUse2,BigDecimal dayUse3,
                    BigDecimal dayUse4, BigDecimal dayUse5, Integer meterType,
                    Integer valveState, LocalDateTime freezingTime,String installAddress,String customerName,String customerPhone) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasMeterNumber = gasMeterNumber;
        this.gasMeterCode = gasMeterCode;
        this.versionName = versionName;
        this.gasMeterBalance = gasMeterBalance;
        this.totalUseGas = totalUseGas;
        this.dayUse1 = dayUse1;
        this.dayUse2 = dayUse2;
        this.dayUse3 = dayUse3;
        this.dayUse4 = dayUse4;
        this.dayUse5 = dayUse5;
        this.meterType = meterType;
        this.valveState = valveState;
        this.freezingTime = freezingTime;
        this.installAddress = installAddress;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

}
