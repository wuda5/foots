package com.cdqckj.gmis.devicearchive.dto;

import java.time.LocalDateTime;
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
 * @since 2020-08-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BatchGasPageDTO", description = "")
public class BatchGasPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 街道id
     */
    @ApiModelProperty(value = "街道id")
    @Length(max = 32, message = "街道id长度不能超过32")
    private String streetId;
    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    @Length(max = 50, message = "小区id长度不能超过50")
    private String communityId;
    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    private String installCode;
    /**
     * 气表厂家id
     */
    @ApiModelProperty(value = "气表厂家id")
    @Length(max = 1, message = "气表厂家id长度不能超过1")
    private String gasFactoryId;
    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @Length(max = 10, message = "版号id长度不能超过10")
    private String versionId;
    /**
     * 栋
     */
    @ApiModelProperty(value = "栋")
    private Integer buildings;
    /**
     * 单元
     */
    @ApiModelProperty(value = "单元")
    private Integer unit;
    /**
     * 楼层
     */
    @ApiModelProperty(value = "楼层")
    private Integer storey;
    /**
     * 户数
     */
    @ApiModelProperty(value = "户数")
    private Integer households;
    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 32, message = "安装地址长度不能超过32")
    private String address;

}
