package com.cdqckj.gmis.nodearchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-08-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_node")
@ApiModel(value = "Node", description = "")
@AllArgsConstructor
public class Node extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司code
     */
    @ApiModelProperty(value = "公司code")
    @Length(max = 32, message = "公司code长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司code")
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
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
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
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID")
    @Length(max = 32, message = "父节点ID长度不能超过32")
    @TableField(value = "parent_id", condition = LIKE)
    @Excel(name = "父节点ID")
    private String parentId;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    @Length(max = 100, message = "节点名称长度不能超过100")
    @TableField(value = "node_name", condition = LIKE)
    @Excel(name = "节点名称")
    private String nodeName;

    /**
     * 节点编号
     */
    @ApiModelProperty(value = "节点编号")
    @Length(max = 30, message = "节点编号长度不能超过30")
    @TableField(value = "node_number", condition = LIKE)
    @Excel(name = "节点编号")
    private String nodeNumber;

    /**
     * 节点底数
     */
    @ApiModelProperty(value = "节点底数")
    @TableField("node_base")
    @Excel(name = "节点底数")
    private BigDecimal nodeBase;

    /**
     * 厂家
     */
    @ApiModelProperty(value = "厂家")
    @Length(max = 32, message = "厂家长度不能超过32")
    @TableField(value = "node_factory_id", condition = LIKE)
    @Excel(name = "厂家")
    private String nodeFactoryId;

    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    @Length(max = 32, message = "型号长度不能超过32")
    @TableField(value = "node_type_id", condition = LIKE)
    @Excel(name = "型号")
    private String nodeTypeId;

    /**
     * 管道口径
     */
    @ApiModelProperty(value = "管道口径")
    @Length(max = 10, message = "管道口径长度不能超过10")
    @TableField(value = "caliber", condition = LIKE)
    @Excel(name = "管道口径")
    private String caliber;

    /**
     * 温度上限
     */
    @ApiModelProperty(value = "温度上限")
    @TableField("upper_temperature_limit")
    @Excel(name = "温度上限")
    private Integer upperTemperatureLimit;

    /**
     * 温度下限
     */
    @ApiModelProperty(value = "温度下限")
    @TableField("lower_temperature_limit")
    @Excel(name = "温度下限")
    private Integer lowerTemperatureLimit;

    /**
     * 压力上限
     */
    @ApiModelProperty(value = "压力上限")
    @TableField("upper_pressure_limit")
    @Excel(name = "压力上限")
    private Integer upperPressureLimit;

    /**
     * 压力下限
     */
    @ApiModelProperty(value = "压力下限")
    @TableField("lower_pressure_limit")
    @Excel(name = "压力下限")
    private Integer lowerPressureLimit;

    /**
     * 流量上限
     */
    @ApiModelProperty(value = "流量上限")
    @TableField("upper_flow_limit")
    @Excel(name = "流量上限")
    private Integer upperFlowLimit;

    /**
     * 流量下限
     */
    @ApiModelProperty(value = "流量下限")
    @TableField("lower_flow_limit")
    @Excel(name = "流量下限")
    private Integer lowerFlowLimit;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    @Excel(name = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /**
     * 负责人ID
     */
    @ApiModelProperty(value = "负责人ID")
    @Length(max = 32, message = "负责人ID长度不能超过32")
    @TableField(value = "principal_user_id", condition = LIKE)
    @Excel(name = "负责人ID")
    private String principalUserId;

    /**
     * 负责人名称
     */
    @ApiModelProperty(value = "负责人名称")
    @Length(max = 100, message = "负责人名称长度不能超过100")
    @TableField(value = "principal_user_name", condition = LIKE)
    @Excel(name = "负责人名称")
    private String principalUserName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 报警开始时间
     */
    @ApiModelProperty(value = "报警开始时间")
    @TableField("alarm_start_time")
    @Excel(name = "报警开始时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime alarmStartTime;

    /**
     * 最新报警时间
     */
    @ApiModelProperty(value = "最新报警时间")
    @TableField("update_alarm_time")
    @Excel(name = "最新报警时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime updateAlarmTime;

    /**
     * 报警状态
     */
    @ApiModelProperty(value = "报警状态")
    @TableField("alarm_status")
    @Excel(name = "报警状态")
    private Integer alarmStatus;

    /**
     * 报警信息
     */
    @ApiModelProperty(value = "报警信息")
    @Length(max = 500, message = "报警信息长度不能超过500")
    @TableField(value = "alarm_content", condition = LIKE)
    @Excel(name = "报警信息")
    private String alarmContent;

    /**
     * 温度
     */
    @ApiModelProperty(value = "温度")
    @TableField("temperature")
    @Excel(name = "温度")
    private BigDecimal temperature;

    /**
     * 压力
     */
    @ApiModelProperty(value = "压力")
    @TableField("pressure")
    @Excel(name = "压力")
    private BigDecimal pressure;

    /**
     * 瞬时流量
     */
    @ApiModelProperty(value = "瞬时流量")
    @TableField("instantaneous_flow_rate")
    @Excel(name = "瞬时流量")
    private BigDecimal instantaneousFlowRate;

    /**
     * 工况流量
     */
    @ApiModelProperty(value = "工况流量")
    @TableField("working_flow")
    @Excel(name = "工况流量")
    private BigDecimal workingFlow;

    /**
     * 标况流量
     */
    @ApiModelProperty(value = "标况流量")
    @TableField("standard_flow")
    @Excel(name = "标况流量")
    private BigDecimal standardFlow;

    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    @TableField("total_use_gas")
    @Excel(name = "累计用气量")
    private BigDecimal totalUseGas;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("data_update_time")
    @Excel(name = "更新时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime dataUpdateTime;

    /**
     * 昨日止数
     */
    @ApiModelProperty(value = "昨日止数")
    @TableField("yesterday_total_gas")
    @Excel(name = "昨日止数")
    private BigDecimal yesterdayTotalGas;

    /**
     * 昨日用量
     */
    @ApiModelProperty(value = "昨日用量")
    @TableField("yesterday_use_gas")
    @Excel(name = "昨日用量")
    private BigDecimal yesterdayUseGas;

    /**
     * 上月止数
     */
    @ApiModelProperty(value = "上月止数")
    @TableField("last_month_total_gas")
    @Excel(name = "上月止数")
    private BigDecimal lastMonthTotalGas;

    /**
     * 上月用量
     */
    @ApiModelProperty(value = "上月用量")
    @TableField("last_month_use_gas")
    @Excel(name = "上月用量")
    private BigDecimal lastMonthUseGas;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    @TableField("delete_user")
    @Excel(name = "删除人")
    private Long deleteUser;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    @TableField("delete_status")
    @Excel(name = "删除标识")
    private Long deleteStatus;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(value = "install_address", condition = LIKE)
    @Excel(name = "安装地址")
    private String installAddress;


    @Builder
    public Node(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String parentId, 
                    String nodeName, String nodeNumber, BigDecimal nodeBase, String nodeFactoryId, String nodeTypeId, String caliber, 
                    Integer upperTemperatureLimit, Integer lowerTemperatureLimit, Integer upperPressureLimit, Integer lowerPressureLimit, Integer upperFlowLimit, Integer lowerFlowLimit, 
                    BigDecimal longitude, BigDecimal latitude, String principalUserId, String principalUserName, String telphone, LocalDateTime alarmStartTime, 
                    LocalDateTime updateAlarmTime, Integer alarmStatus, String alarmContent, BigDecimal temperature, BigDecimal pressure, BigDecimal instantaneousFlowRate, 
                    BigDecimal workingFlow, BigDecimal standardFlow, BigDecimal totalUseGas, LocalDateTime dataUpdateTime, BigDecimal yesterdayTotalGas, BigDecimal yesterdayUseGas, 
                    BigDecimal lastMonthTotalGas, BigDecimal lastMonthUseGas, Integer dataStatus, LocalDateTime deleteTime, Long deleteUser, Long deleteStatus, String installAddress) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.parentId = parentId;
        this.nodeName = nodeName;
        this.nodeNumber = nodeNumber;
        this.nodeBase = nodeBase;
        this.nodeFactoryId = nodeFactoryId;
        this.nodeTypeId = nodeTypeId;
        this.caliber = caliber;
        this.upperTemperatureLimit = upperTemperatureLimit;
        this.lowerTemperatureLimit = lowerTemperatureLimit;
        this.upperPressureLimit = upperPressureLimit;
        this.lowerPressureLimit = lowerPressureLimit;
        this.upperFlowLimit = upperFlowLimit;
        this.lowerFlowLimit = lowerFlowLimit;
        this.longitude = longitude;
        this.latitude = latitude;
        this.principalUserId = principalUserId;
        this.principalUserName = principalUserName;
        this.telphone = telphone;
        this.alarmStartTime = alarmStartTime;
        this.updateAlarmTime = updateAlarmTime;
        this.alarmStatus = alarmStatus;
        this.alarmContent = alarmContent;
        this.temperature = temperature;
        this.pressure = pressure;
        this.instantaneousFlowRate = instantaneousFlowRate;
        this.workingFlow = workingFlow;
        this.standardFlow = standardFlow;
        this.totalUseGas = totalUseGas;
        this.dataUpdateTime = dataUpdateTime;
        this.yesterdayTotalGas = yesterdayTotalGas;
        this.yesterdayUseGas = yesterdayUseGas;
        this.lastMonthTotalGas = lastMonthTotalGas;
        this.lastMonthUseGas = lastMonthUseGas;
        this.dataStatus = dataStatus;
        this.deleteTime = deleteTime;
        this.deleteUser = deleteUser;
        this.deleteStatus = deleteStatus;
        this.installAddress = installAddress;
    }

}
