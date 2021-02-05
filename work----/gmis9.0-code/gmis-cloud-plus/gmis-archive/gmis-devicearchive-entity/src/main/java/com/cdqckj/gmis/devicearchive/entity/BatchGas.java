package com.cdqckj.gmis.devicearchive.entity;

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
 * @since 2020-08-14
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_batch_gas")
@ApiModel(value = "BatchGas", description = "")
@AllArgsConstructor
public class BatchGas extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 街道id
     */
    @ApiModelProperty(value = "街道id")
    @Length(max = 32, message = "街道id长度不能超过32")
    @TableField(value = "street_id", condition = LIKE)
    @Excel(name = "街道id")
    private String streetId;

    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    @Length(max = 50, message = "小区id长度不能超过50")
    @TableField(value = "community_id", condition = LIKE)
    @Excel(name = "小区id")
    private String communityId;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    @TableField(value = "install_code", condition = LIKE)
    @Excel(name = "报装编号")
    private String installCode;

    /**
     * 气表厂家id
     */
    @ApiModelProperty(value = "气表厂家id")
    @Length(max = 1, message = "气表厂家id长度不能超过1")
    @TableField(value = "gas_factory_id", condition = LIKE)
    @Excel(name = "气表厂家id")
    private String gasFactoryId;

    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @Length(max = 10, message = "版号id长度不能超过10")
    @TableField(value = "version_id", condition = LIKE)
    @Excel(name = "版号id")
    private String versionId;

    /**
     * 栋
     */
    @ApiModelProperty(value = "栋")
    @TableField("buildings")
    @Excel(name = "栋")
    private Integer buildings;

    /**
     * 单元
     */
    @ApiModelProperty(value = "单元")
    @TableField("unit")
    @Excel(name = "单元")
    private Integer unit;

    /**
     * 楼层
     */
    @ApiModelProperty(value = "楼层")
    @TableField("storey")
    @Excel(name = "楼层")
    private Integer storey;

    /**
     * 户数
     */
    @ApiModelProperty(value = "户数")
    @TableField("households")
    @Excel(name = "户数")
    private Integer households;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 32, message = "安装地址长度不能超过32")
    @TableField(value = "address", condition = LIKE)
    @Excel(name = "安装地址")
    private String address;


    @Builder
    public BatchGas(Long id, 
                    String streetId, String communityId, String installCode, String gasFactoryId, String versionId, 
                    Integer buildings, Integer unit, Integer storey, Integer households, String address) {
        this.id = id;
        this.streetId = streetId;
        this.communityId = communityId;
        this.installCode = installCode;
        this.gasFactoryId = gasFactoryId;
        this.versionId = versionId;
        this.buildings = buildings;
        this.unit = unit;
        this.storey = storey;
        this.households = households;
        this.address = address;
    }

}
