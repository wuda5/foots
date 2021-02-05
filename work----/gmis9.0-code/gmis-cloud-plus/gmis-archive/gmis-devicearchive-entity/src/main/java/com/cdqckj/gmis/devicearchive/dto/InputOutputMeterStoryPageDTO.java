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
 * @since 2020-08-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InputOutputMeterStoryPageDTO", description = "")
public class InputOutputMeterStoryPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    private String companyCode;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String companyName;
    @ApiModelProperty(value = "")
    private Long orgId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String orgName;
    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    @Length(max = 100, message = "批次号长度不能超过100")
    private String serialCode;
    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    private String gasMeterFactoryId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @Length(max = 32, message = "版号id长度不能超过32")
    private String gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 普表
     *             卡表
     *             物联网表
     *             流量计(普表)
     *             流量计(卡表）
     *             流量计(物联网表）
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 32, message = "普表长度不能超过32")
    private String gasMeterTypeId;
    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    private String gasMeterTypeName;
    /**
     * 1表示出库
     * 			0表示入库
     * 		
     */
    @ApiModelProperty(value = "1表示出库")
    private Integer status;
    /**
     * 表示库存数量
     */
    @ApiModelProperty(value = "表示库存数量")
    private Long storeCount;

}
