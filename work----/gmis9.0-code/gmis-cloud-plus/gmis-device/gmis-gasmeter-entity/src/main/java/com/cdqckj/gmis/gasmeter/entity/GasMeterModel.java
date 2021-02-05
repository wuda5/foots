package com.cdqckj.gmis.gasmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 气表型号
 * </p>
 *
 * @author gmis
 * @since 2020-08-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pt_gas_meter_model")
@ApiModel(value = "GasMeterModel", description = "气表型号")
@AllArgsConstructor
public class GasMeterModel extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 生产厂家ID
     */
    @ApiModelProperty(value = "生产厂家ID",required = true)
    @NotNull(message = "生产厂家ID不能为空")
    @TableField("company_id")
    @Excel(name = "生产厂家ID")
    private Long companyId;

    /**
     * 生产厂家编号
     */
    @ApiModelProperty(value = "生产厂家编号")
    @Length(max = 10, message = "生产厂家编号长度不能超过10")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "生产厂家编号")
    private String companyCode;

    /**
     * 生产厂家名称
     */
    @ApiModelProperty(value = "生产厂家名称")
    @Length(max = 100, message = "生产厂家名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "生产厂家名称")
    private String companyName;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID",required = true)
    @NotNull(message = "版号ID不能为空")
    @TableField("gas_meter_version_id")
    @Excel(name = "版号ID")
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
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称",required = true)
    @NotEmpty(message = "型号名称不能为空")
    @Length(max = 30, message = "型号名称长度不能超过30")
    @TableField(value = "model_name", condition = LIKE)
    @Excel(name = "型号名称")
    private String modelName;

    /**
     * 是否启用（1：启用；0-禁用）
     */
    @ApiModelProperty(value = "是否启用（1：启用；0-禁用）")
    @TableField("data_status")
    @Excel(name = "是否启用（1：启用；0-禁用）")
    private Integer dataStatus;

    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格",required = true)
    @NotNull(message = "型号规格不能为空")
    @TableField("specification")
    @Excel(name = "型号规格")
    private String specification;

    /**
     * 公称流量
     */
    @ApiModelProperty(value = "公称流量",required = true)
    @TableField("nominal_flow_rate")
    @Excel(name = "公称流量")
    private BigDecimal nominalFlowRate;

    /**
     * 最大流量
     */
    @ApiModelProperty(value = "最大流量",required = true)
    @TableField("max_flow")
    @Excel(name = "最大流量")
    private BigDecimal maxFlow;

    /**
     * 最小流量
     */
    @ApiModelProperty(value = "最小流量",required = true)
    @TableField("min_flow")
    @Excel(name = "最小流量")
    private BigDecimal minFlow;

    /**
     * 最大压力
     */
    @ApiModelProperty(value = "最大压力",required = true)
    @TableField("max_pressure")
    @Excel(name = "最大压力")
    private BigDecimal maxPressure;

    /**
     * 字轮最大值
     */
    @ApiModelProperty(value = "字轮最大值",required = true)
    @TableField("max_word_wheel")
    @Excel(name = "字轮最大值")
    private BigDecimal maxWordWheel;

    /**
     * 回转体积
     */
    @ApiModelProperty(value = "回转体积",required = true)
    @TableField("rotational_volume")
    @Excel(name = "回转体积")
    private BigDecimal rotationalVolume;

    /**
     * 删除标识（0：存在；1-删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1-删除）")
    @TableField("delete_status")
    @Excel(name = "删除标识（0：存在；1-删除）")
    private Integer deleteStatus;


    @Builder
    public GasMeterModel(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    Long companyId, String companyCode, String companyName, Long gasMeterVersionId, String gasMeterVersionName, 
                    String modelName, Integer dataStatus, String specification, BigDecimal nominalFlowRate, BigDecimal maxFlow, BigDecimal minFlow,
                    BigDecimal maxPressure, BigDecimal maxWordWheel, BigDecimal rotationalVolume, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyId = companyId;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.modelName = modelName;
        this.dataStatus = dataStatus;
        this.specification = specification;
        this.nominalFlowRate = nominalFlowRate;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
        this.maxPressure = maxPressure;
        this.maxWordWheel = maxWordWheel;
        this.rotationalVolume = rotationalVolume;
        this.deleteStatus = deleteStatus;
    }

}
