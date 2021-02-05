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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;


/**
 * <p>
 * 实体类
 * 物联网气表上报数据
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
@TableName("qw_iot_gas_meter_upload_data")
@ApiModel(value = "IotGasMeterUploadData", description = "物联网气表上报数据")
@AllArgsConstructor
public class IotGasMeterUploadData extends Entity<Long> {

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
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 厂家id
     */
    @ApiModelProperty(value = "厂家id")
    @TableField("gas_meter_factory_id")
    @Excel(name = "厂家id")
    private Long gasMeterFactoryId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String gasMeterFactoryName;

    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @TableField("gas_meter_version_id")
    @Excel(name = "版号id")
    private Long gasMeterVersionId;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    @TableField(value = "gas_meter_version_name", condition = LIKE)
    @Excel(name = "版号名称")
    private String gasMeterVersionName;

    /**
     * 型号id
     */
    @ApiModelProperty(value = "型号id")
    @TableField("gas_meter_model_id")
    @Excel(name = "型号id")
    private Long gasMeterModelId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 30, message = "型号名称长度不能超过30")
    @TableField(value = "gas_meter_model_name", condition = LIKE)
    @Excel(name = "型号名称")
    private String gasMeterModelName;

    /**
     * 设备档案id
     */
    @ApiModelProperty(value = "设备档案id")
    @Length(max = 255, message = "设备档案id长度不能超过255")
    @TableField(value = "archive_id", condition = LIKE)
    @Excel(name = "设备档案id")
    private String archiveId;

    /**
     * 逻辑域id
     */
    @ApiModelProperty(value = "逻辑域id")
    @Length(max = 255, message = "逻辑域id长度不能超过255")
    @TableField(value = "domain_id", condition = LIKE)
    @Excel(name = "设备档案id")
    private String domainId;

    /**
     * 气表id
     */
    @ApiModelProperty(value = "气表id")
    @TableField("gas_meter_code")
    @Excel(name = "气表id")
    private String gasMeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 初始气量
     */
    @ApiModelProperty(value = "初始气量")
    @TableField("initial_measurement_base")
    @Excel(name = "初始气量")
    private BigDecimal initialMeasurementBase;

    /**
     * 累计充值量
     */
    @ApiModelProperty(value = "累计充值量")
    @TableField("total_charge_value")
    @Excel(name = "累计充值量")
    private BigDecimal totalChargeValue;

    /**
     * 日用金额
     */
    @ApiModelProperty(value = "日用金额")
    @TableField("day_use_money")
    @Excel(name = "日用金额")
    private BigDecimal dayUseMoney;

    /**
     * 日用氣量
     */
    @ApiModelProperty(value = "日用氣量")
    @TableField("day_use_gas")
    @Excel(name = "日用氣量")
    private BigDecimal dayUseGas;

    /**
     * 充值次数
     */
    @ApiModelProperty(value = "充值次数")
    @TableField("charge_count")
    @Excel(name = "充值次数")
    private Integer chargeCount;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 价格方案id
     */
    @ApiModelProperty(value = "价格方案id")
    @TableField("price_scheme_id")
    @Excel(name = "价格方案id")
    private Long priceSchemeId;

    /**
     * 当前阶梯
     */
    @ApiModelProperty(value = "当前阶梯")
    @TableField("current_ladder")
    @Excel(name = "当前阶梯")
    private Integer currentLadder;

    /**
     * 当前价格
     */
    @ApiModelProperty(value = "当前价格")
    @TableField("current_price")
    @Excel(name = "当前价格")
    private BigDecimal currentPrice;

    /**
     * 阀门状态
     */
    @ApiModelProperty(value = "阀门状态")
    @TableField("valve_status")
    @Excel(name = "阀门状态")
    private Integer valveStatus;

    /**
     * 周期累计用气量
     */
    @ApiModelProperty(value = "周期累计用气量")
    @TableField("cycle_total_use_gas")
    @Excel(name = "周期累计用气量")
    private BigDecimal cycleTotalUseGas;

    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    @TableField("total_use_gas")
    @Excel(name = "累计用气量")
    private BigDecimal totalUseGas;

    /**
     * 冻结时间
     */
    @ApiModelProperty(value = "冻结时间")
    @TableField("freezing_time")
    @Excel(name = "冻结时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime freezingTime;

    /**
     * 是否在线
     */
    @ApiModelProperty(value = "是否在线")
    @TableField("is_online")
    @Excel(name = "是否在线")
    private Integer isOnline;

    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格")
    @TableField("specification")
    @Excel(name = "型号规格")
    private String specification;

    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    @TableField("direction")
    @Excel(name = "通气方向")
    private String direction;

    /**
     * 通气时间
     */
    @ApiModelProperty(value = "通气时间")
    @TableField("ventilate_time")
    private LocalDateTime ventilateTime;

    /**
     * 安装时间
     */
    @ApiModelProperty(value = "安装时间")
    @TableField("install_time")
    private LocalDateTime installTime;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 设备状态
     */
    @ApiModelProperty(value = "设备状态")
    @TableField("device_state")
    @Excel(name = "设备状态")
    private Integer deviceState;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @TableField("customer_code")
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @TableField("customer_name")
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 户表地址
     */
    @ApiModelProperty(value = "户表地址")
    @TableField("meter_address")
    @Excel(name = "户表地址")
    private String meterAddress;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @TableField("use_gas_type_name")
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 上报时间
     */
    @ApiModelProperty(value = "上报时间")
    @TableField("upload_time")
    private LocalDateTime uploadTime;

    /**
     * 抄表数据上报次数
     */
    @ApiModelProperty(value = "抄表数据上报次数")
    @TableField("day_read_num")
    @Excel(name = "抄表数据上报次数")
    private Integer dayReadNum;


    @Builder
    public IotGasMeterUploadData(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String gasMeterNumber,
                    Long gasMeterFactoryId, String gasMeterFactoryName, Long gasMeterVersionId, String gasMeterVersionName, String archiveId, String gasMeterCode,
                    String gasMeterName,BigDecimal initialMeasurementBase, BigDecimal totalChargeValue,
                    BigDecimal dayUseMoney, Integer chargeCount, Long useGasTypeId, Long priceSchemeId, Integer currentLadder, BigDecimal currentPrice, 
                    Integer valveStatus, BigDecimal cycleTotalUseGas, BigDecimal totalUseGas,LocalDateTime freezingTime, Integer isOnline, BigDecimal dayUseGas,String domainId,
                                 String gasMeterModelName,Long gasMeterModelId,String specification,String direction,
                                 LocalDateTime ventilateTime, LocalDateTime installTime,BigDecimal longitude,
                    BigDecimal latitude,Integer deviceState,String customerCode,String customerName,
                                 String meterAddress,String useGasTypeName,LocalDateTime uploadTime,Integer dayReadNum) {
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
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.archiveId = archiveId;
        this.domainId = domainId;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterName = gasMeterName;
        this.initialMeasurementBase = initialMeasurementBase;
        this.totalChargeValue = totalChargeValue;
        this.dayUseMoney = dayUseMoney;
        this.chargeCount = chargeCount;
        this.useGasTypeId = useGasTypeId;
        this.priceSchemeId = priceSchemeId;
        this.currentLadder = currentLadder;
        this.currentPrice = currentPrice;
        this.valveStatus = valveStatus;
        this.cycleTotalUseGas = cycleTotalUseGas;
        this.totalUseGas = totalUseGas;
        this.freezingTime = freezingTime;
        this.isOnline = isOnline;
        this.dayUseGas = dayUseGas;
        this.gasMeterModelName = gasMeterModelName;
        this.gasMeterModelId = gasMeterModelId;
        this.specification = specification;
        this.direction = direction;
        this.ventilateTime = ventilateTime;
        this.installTime =installTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.deviceState = deviceState;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.meterAddress = meterAddress;
        this.useGasTypeName = useGasTypeName;
        this.uploadTime = uploadTime;
        this.dayReadNum = dayReadNum;
    }

}
