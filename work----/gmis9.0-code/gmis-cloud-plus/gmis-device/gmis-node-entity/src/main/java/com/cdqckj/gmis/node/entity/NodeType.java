package com.cdqckj.gmis.node.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-07-21
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pt_node_type")
@ApiModel(value = "NodeType", description = "")
@AllArgsConstructor
public class NodeType extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @Length(max = 32, message = "厂家ID长度不能超过32")
    @TableField(value = "node_facotry_id", condition = LIKE)
    @Excel(name = "厂家ID")
    private String nodeFacotryId;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 50, message = "型号名称长度不能超过50")
    @TableField(value = "node_name", condition = LIKE)
    @Excel(name = "型号名称")
    private String nodeName;

    /**
     * 型号编码
     */
    @ApiModelProperty(value = "型号编码")
    @Length(max = 10, message = "型号编码长度不能超过10")
    @TableField(value = "node_code", condition = LIKE)
    @Excel(name = "型号编码")
    private String nodeCode;

    /**
     * 型号说明
     */
    @ApiModelProperty(value = "型号说明")
    @Length(max = 100, message = "型号说明长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "型号说明")
    private String remark;

    /**
     * RTU:rtu设备
     *             FLOWMETER:流量计
     */
    @ApiModelProperty(value = "RTU:rtu设备")
    @Length(max = 10, message = "RTU:rtu设备长度不能超过10")
    @TableField(value = "type", condition = LIKE)
    @Excel(name = "RTU:rtu设备")
    private String type;

    /**
     * TEMPERATURE:温度监测
     *             PRESSURE:压力监测
     *             FLOW:流量监测
     *             VALVE:阀门控制
     * 
     *             注：多选
     * 
     *             
     */
    @ApiModelProperty(value = "TEMPERATURE:温度监测")
    @Length(max = 500, message = "TEMPERATURE:温度监测长度不能超过500")
    @TableField(value = "function", condition = LIKE)
    @Excel(name = "TEMPERATURE:温度监测")
    private String function;

    /**
     * 启用
     *             禁用
     */
    @ApiModelProperty(value = "启用")
    @TableField("node_status")
    @Excel(name = "启用")
    private Integer nodeStatus;


    @Builder
    public NodeType(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String nodeFacotryId, String nodeName, String nodeCode, String remark, String type, 
                    String function, Integer nodeStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.nodeFacotryId = nodeFacotryId;
        this.nodeName = nodeName;
        this.nodeCode = nodeCode;
        this.remark = remark;
        this.type = type;
        this.function = function;
        this.nodeStatus = nodeStatus;
    }

}
