package com.cdqckj.gmis.gasmeter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterModelSaveDTO", description = "气表型号")
public class GasMeterModelSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 生产厂家ID
     */
    @ApiModelProperty(value = "生产厂家ID",required = true)
    @NotNull(message = "生产厂家ID不能为空")
    private Long companyId;
    /**
     * 生产厂家编号
     */
    @ApiModelProperty(value = "生产厂家编号")
    @Length(max = 10, message = "生产厂家编号长度不能超过10")
    private String companyCode;
    /**
     * 生产厂家名称
     */
    @ApiModelProperty(value = "生产厂家名称")
    @Length(max = 100, message = "生产厂家名称长度不能超过100")
    private String companyName;
    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID",required = true)
    @NotNull(message = "版号ID不能为空")
    private Long gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称",required = true)
    @NotEmpty(message = "型号名称不能为空")
    @Length(max = 30, message = "型号名称长度不能超过30")
    private String modelName;
    /**
     * 是否启用（0：启用；1-禁用）
     */
    @ApiModelProperty(value = "是否启用（0：启用；1-禁用）")
    private Integer dataStatus;
    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格",required = true)
    @NotNull(message = "型号规格不能为空")
    private String specification;
    /**
     * 公称流量
     */
    @ApiModelProperty(value = "公称流量",required = true)
    private BigDecimal nominalFlowRate;
    /**
     * 最大流量
     */
    @ApiModelProperty(value = "最大流量",required = true)
    private BigDecimal maxFlow;
    /**
     * 最小流量
     */
    @ApiModelProperty(value = "最小流量",required = true)
    private BigDecimal minFlow;
    /**
     * 最大压力
     */
    @ApiModelProperty(value = "最大压力",required = true)
    private BigDecimal maxPressure;
    /**
     * 字轮最大值
     */
    @ApiModelProperty(value = "字轮最大值",required = true)
    private BigDecimal maxWordWheel;
    /**
     * 回转体积
     */
    @ApiModelProperty(value = "回转体积",required = true)
    private BigDecimal rotationalVolume;
    /**
     * 删除标识（0：存在；1-删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1-删除）")
    private Integer deleteStatus;

}
