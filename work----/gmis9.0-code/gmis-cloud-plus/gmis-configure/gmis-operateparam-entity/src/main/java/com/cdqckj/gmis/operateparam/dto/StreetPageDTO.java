package com.cdqckj.gmis.operateparam.dto;

import java.time.LocalDateTime;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "StreetPageDTO", description = "")
public class StreetPageDTO implements Serializable {

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
     * 省code
     */
    @ApiModelProperty(value = "省code")
    private String provinceCode;
    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    @Length(max = 255, message = "省名称长度不能超过255")
    private String provinceName;
    /**
     * 区code
     */
    @ApiModelProperty(value = "区code")
    private String areaCode;
    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    @Length(max = 50, message = "区名称长度不能超过50")
    private String areaName;
    /**
     * 市code
     */
    @ApiModelProperty(value = "市code")
    private String cityCode;
    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    @Length(max = 255, message = "市名称长度不能超过255")
    private String cityName;
    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称")
    @Length(max = 100, message = "街道名称长度不能超过100")
    private String streetName;
    /**
     * 状态（1-生效，0-无效）
     */
    @ApiModelProperty(value = "状态（1-生效，0-无效）")
    private Integer dataStatus;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除用户id
     */
    @ApiModelProperty(value = "删除用户id")
    private Long deleteUser;

    /**
     * 栋(组)ID
     */
    @ApiModelProperty(value = "栋(组)ID")
    @Length(max = 32, message = "栋(组)ID长度不能超过32")
    private Long storiedId;
    /**
     *栋(组)名称
     */
    @ApiModelProperty(value = "栋(组)名称")
    @Length(max = 255, message = "栋(组)名称长度不能超过255")
    private String storiedName;
    /**
     * 单元ID
     */
    @ApiModelProperty(value = "单元ID")
    @Length(max = 32, message = "单元ID长度不能超过32")
    private Long unitId;
    /**
     *单元名称
     */
    @ApiModelProperty(value = "单元名称")
    @Length(max = 255, message = "单元名称长度不能超过255")
    private String unitName;

    /**
     * 状态（0-生效，1-删除）
     */
    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    private Integer deleteStatus;
}
