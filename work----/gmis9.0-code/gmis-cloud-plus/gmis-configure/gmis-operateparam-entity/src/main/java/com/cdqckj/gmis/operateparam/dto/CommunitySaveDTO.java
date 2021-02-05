package com.cdqckj.gmis.operateparam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CommunitySaveDTO", description = "")
public class CommunitySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    @Length(max = 32, message = "公司ID长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    private Long streetId;
    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称")
    @Length(max = 50, message = "小区名称长度不能超过50")
    private String communityName;
    /**
     * 小区地址
     */
    @ApiModelProperty(value = "小区地址")
    @Length(max = 100, message = "小区地址长度不能超过100")
    private String communityAddress;
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
     * 表数
     */
    @ApiModelProperty(value = "表数")
    private Integer meterCount;
    /**
     * 已拆数
     */
    @ApiModelProperty(value = "已拆数")
    private Integer dismantleCount;
    /**
     * 状态（1-生效，0-无效）
     */
    @ApiModelProperty(value = "状态（1-生效，0-无效）")
    private Integer dataStatus;
    /**
     * 状态（0-生效，1-删除）
     */
    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    private Integer deleteStatus;
}
