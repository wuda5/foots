package com.cdqckj.gmis.operateparam.dto;

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
import com.cdqckj.gmis.base.entity.SuperEntity;
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
 * 价格方案映射表
 * </p>
 *
 * @author gmis
 * @since 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PriceMappingUpdateDTO", description = "价格方案映射表")
public class PriceMappingUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 价格id
     */
    @ApiModelProperty(value = "价格id")
    private Long priceId;
    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号")
    private Integer priceNum;
    /**
     * 用气类型号
     */
    @ApiModelProperty(value = "用气类型号")
    private Integer useGasTypeNum;
    /**
     * 气表档案编号
     */
    @ApiModelProperty(value = "气表档案编号")
    @Length(max = 255, message = "气表档案编号长度不能超过255")
    private String gasMeterCode;
    /**
     * 上次价格方案id
     */
    @ApiModelProperty(value = "上次价格方案id")
    private Long prePriceId;
    /**
     * 上次价格号
     */
    @ApiModelProperty(value = "上次价格号")
    private Integer prePriceNum;
    /**
     * 上次用气类型号
     */
    @ApiModelProperty(value = "上次用气类型号")
    private Integer preUseGasTypeNum;
    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    private Long useGasTypeId;
}
