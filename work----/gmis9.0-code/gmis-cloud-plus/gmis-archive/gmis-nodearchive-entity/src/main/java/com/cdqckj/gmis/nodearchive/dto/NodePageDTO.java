package com.cdqckj.gmis.nodearchive.dto;

import java.time.LocalDateTime;
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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "NodePageDTO", description = "")
public class NodePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司code
     */
    @ApiModelProperty(value = "公司code")
    @Length(max = 32, message = "公司code长度不能超过32")
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
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID")
    @Length(max = 32, message = "父节点ID长度不能超过32")
    private String parentId;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    @Length(max = 100, message = "节点名称长度不能超过100")
    private String nodeName;
    /**
     * 节点编号
     */
    @ApiModelProperty(value = "节点编号")
    @Length(max = 30, message = "节点编号长度不能超过30")
    private String nodeNumber;
    /**
     * 节点底数
     */
    @ApiModelProperty(value = "节点底数")
    private BigDecimal nodeBase;
    /**
     * 厂家
     */
    @ApiModelProperty(value = "厂家")
    @Length(max = 32, message = "厂家长度不能超过32")
    private String nodeFactoryId;
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    @Length(max = 32, message = "型号长度不能超过32")
    private String nodeTypeId;
    /**
     * 管道口径
     */
    @ApiModelProperty(value = "管道口径")
    @Length(max = 10, message = "管道口径长度不能超过10")
    private String caliber;
    /**
     * 温度上限
     */
    @ApiModelProperty(value = "温度上限")
    private Integer upperTemperatureLimit;
    /**
     * 温度下限
     */
    @ApiModelProperty(value = "温度下限")
    private Integer lowerTemperatureLimit;
    /**
     * 压力上限
     */
    @ApiModelProperty(value = "压力上限")
    private Integer upperPressureLimit;
    /**
     * 压力下限
     */
    @ApiModelProperty(value = "压力下限")
    private Integer lowerPressureLimit;
    /**
     * 流量上限
     */
    @ApiModelProperty(value = "流量上限")
    private Integer upperFlowLimit;
    /**
     * 流量下限
     */
    @ApiModelProperty(value = "流量下限")
    private Integer lowerFlowLimit;
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
     * 负责人ID
     */
    @ApiModelProperty(value = "负责人ID")
    @Length(max = 32, message = "负责人ID长度不能超过32")
    private String principalUserId;
    /**
     * 负责人名称
     */
    @ApiModelProperty(value = "负责人名称")
    @Length(max = 100, message = "负责人名称长度不能超过100")
    private String principalUserName;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;
    /**
     * 报警开始时间
     */
    @ApiModelProperty(value = "报警开始时间")
    private LocalDateTime alarmStartTime;
    /**
     * 最新报警时间
     */
    @ApiModelProperty(value = "最新报警时间")
    private LocalDateTime updateAlarmTime;
    /**
     * 报警状态
     */
    @ApiModelProperty(value = "报警状态")
    private Integer alarmStatus;
    /**
     * 报警信息
     */
    @ApiModelProperty(value = "报警信息")
    @Length(max = 500, message = "报警信息长度不能超过500")
    private String alarmContent;
    /**
     * 温度
     */
    @ApiModelProperty(value = "温度")
    private BigDecimal temperature;
    /**
     * 压力
     */
    @ApiModelProperty(value = "压力")
    private BigDecimal pressure;
    /**
     * 瞬时流量
     */
    @ApiModelProperty(value = "瞬时流量")
    private BigDecimal instantaneousFlowRate;
    /**
     * 工况流量
     */
    @ApiModelProperty(value = "工况流量")
    private BigDecimal workingFlow;
    /**
     * 标况流量
     */
    @ApiModelProperty(value = "标况流量")
    private BigDecimal standardFlow;
    /**
     * 累计用气量
     */
    @ApiModelProperty(value = "累计用气量")
    private BigDecimal totalUseGas;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime dataUpdateTime;
    /**
     * 昨日止数
     */
    @ApiModelProperty(value = "昨日止数")
    private BigDecimal yesterdayTotalGas;
    /**
     * 昨日用量
     */
    @ApiModelProperty(value = "昨日用量")
    private BigDecimal yesterdayUseGas;
    /**
     * 上月止数
     */
    @ApiModelProperty(value = "上月止数")
    private BigDecimal lastMonthTotalGas;
    /**
     * 上月用量
     */
    @ApiModelProperty(value = "上月用量")
    private BigDecimal lastMonthUseGas;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deleteUser;
    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Long deleteStatus;
    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String installAddress;

}
