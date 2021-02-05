package com.cdqckj.gmis.operateparam.entity;

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
 * 价格方案映射表
 * </p>
 *
 * @author gmis
 * @since 2020-12-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_price_mapping")
@ApiModel(value = "PriceMapping", description = "价格方案映射表")
@AllArgsConstructor
public class PriceMapping extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 价格id
     */
    @ApiModelProperty(value = "价格id")
    @TableField("price_id")
    @Excel(name = "价格id")
    private Long priceId;

    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号")
    @TableField("price_num")
    @Excel(name = "价格号")
    private Integer priceNum;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 用气类型号
     */
    @ApiModelProperty(value = "用气类型号")
    @TableField("use_gas_type_num")
    @Excel(name = "用气类型号")
    private Integer useGasTypeNum;

    /**
     * 气表档案编号
     */
    @ApiModelProperty(value = "气表档案编号")
    @Length(max = 255, message = "气表档案编号长度不能超过255")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表档案编号")
    private String gasMeterCode;

    /**
     * 上次价格方案id
     */
    @ApiModelProperty(value = "上次价格方案id")
    @TableField("pre_price_id")
    @Excel(name = "上次价格方案id")
    private Long prePriceId;

    /**
     * 上次价格号
     */
    @ApiModelProperty(value = "上次价格号")
    @TableField("pre_price_num")
    @Excel(name = "上次价格号")
    private Integer prePriceNum;

    /**
     * 上次用气类型号
     */
    @ApiModelProperty(value = "上次用气类型号")
    @TableField("pre_use_gas_type_num")
    @Excel(name = "上次用气类型号")
    private Integer preUseGasTypeNum;


    @Builder
    public PriceMapping(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    Long priceId, Integer priceNum, Integer useGasTypeNum, String gasMeterCode, Long prePriceId, 
                    Integer prePriceNum, Integer preUseGasTypeNum, Long useGasTypeId) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.priceId = priceId;
        this.priceNum = priceNum;
        this.useGasTypeNum = useGasTypeNum;
        this.gasMeterCode = gasMeterCode;
        this.prePriceId = prePriceId;
        this.prePriceNum = prePriceNum;
        this.preUseGasTypeNum = preUseGasTypeNum;
        this.useGasTypeId = useGasTypeId;
    }

}
