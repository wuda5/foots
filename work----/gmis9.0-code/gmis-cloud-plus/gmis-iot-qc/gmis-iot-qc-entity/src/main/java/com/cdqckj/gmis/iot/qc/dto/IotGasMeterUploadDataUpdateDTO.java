package com.cdqckj.gmis.iot.qc.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;


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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotGasMeterUploadDataUpdateDTO", description = "物联网气表上报数据")
public class IotGasMeterUploadDataUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
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
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;
    /**
     * 厂家id
     */
    @ApiModelProperty(value = "厂家id")
    private Long gasMeterFactoryId;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    private Long gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 型号id
     */
    @ApiModelProperty(value = "型号id")
    private Long gasMeterModelId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 30, message = "型号名称长度不能超过30")
    private String gasMeterModelName;
    /**
     * 设备档案id
     */
    @ApiModelProperty(value = "设备档案id")
    @Length(max = 255, message = "设备档案id长度不能超过255")
    private String archiveId;
    /**
     * 气表id
     */
    @ApiModelProperty(value = "气表id")
    private String gasMeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    @Length(max = 100, message = "流水号长度不能超过100")
    private String serialNumber;
    /**
     * 初始气量
     */
    @ApiModelProperty(value = "初始气量")
    private BigDecimal initialMeasurementBase;
    /**
     * 累计充值量
     */
    @ApiModelProperty(value = "累计充值量")
    private BigDecimal totalChargeValue;
    /**
     * 日用金额
     */
    @ApiModelProperty(value = "日用金额")
    private BigDecimal dayUseMoney;

    /**
     * 日用氣量
     */
    @ApiModelProperty(value = "日用氣量")
    private BigDecimal dayUseGas;
    /**
     * 充值次数
     */
    @ApiModelProperty(value = "充值次数")
    private Integer chargeCount;
    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    private Long useGasTypeId;
    /**
     * 价格方案id
     */
    @ApiModelProperty(value = "价格方案id")
    private Long priceSchemeId;
    /**
     * 当前阶梯
     */
    @ApiModelProperty(value = "当前阶梯")
    private Integer currentLadder;
    /**
     * 当前价格
     */
    @ApiModelProperty(value = "当前价格")
    private BigDecimal currentPrice;
    /**
     * 阀门状态
     */
    @ApiModelProperty(value = "阀门状态")
    private Integer valveStatus;

    /**
     * 周期累计用气量
     */
    @ApiModelProperty(value = "周期累计用气量")
    private BigDecimal cycleTotalUseGas;

    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    private BigDecimal totalUseGas;

    /**
     * 冻结时间
     */
    @ApiModelProperty(value = "冻结时间")
    private LocalDateTime freezingTime;

    /**
     * 是否在线
     */
    @ApiModelProperty(value = "是否在线")
    private Integer isOnline;
    /**
     * 逻辑域id
     */
    @ApiModelProperty(value = "逻辑域id")
    private String domainId;
    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格")
    private String specification;
    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    private String direction;

    /**
     * 通气时间
     */
    @ApiModelProperty(value = "通气时间")
    private LocalDateTime ventilateTime;

    /**
     * 安装时间
     */
    @ApiModelProperty(value = "安装时间")
    private LocalDateTime installTime;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    /**
     * 设备状态
     */
    @ApiModelProperty(value = "设备状态")
    private Integer deviceState;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 户表地址
     */
    @ApiModelProperty(value = "户表地址")
    private String meterAddress;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    private String useGasTypeName;

    /**
     * 上报时间
     */
    @ApiModelProperty(value = "上报时间")
    private LocalDateTime uploadTime;

    /**
     * 抄表数据上报次数
     */
    @ApiModelProperty(value = "抄表数据上报次数")
    private Integer dayReadNum;
}
