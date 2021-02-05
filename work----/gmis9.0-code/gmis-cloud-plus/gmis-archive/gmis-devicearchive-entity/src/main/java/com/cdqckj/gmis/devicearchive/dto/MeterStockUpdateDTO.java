package com.cdqckj.gmis.devicearchive.dto;

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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "MeterStockUpdateDTO", description = "")
public class MeterStockUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 30, message = "气表编号长度不能超过30")
    private String gasCode;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long gasMeterFactoryId;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 表类型
     */
    @ApiModelProperty(value = "表类型")
    private Long gasMeterTypeId;
    /**
     * 表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）
     */
    @ApiModelProperty(value = "表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）")
    @Length(max = 30, message = "表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）长度不能超过30")
    private String gasMeterTypeName;
    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间")
    private LocalDateTime buyGas;
    /**
     * 检定时间
     */
    @ApiModelProperty(value = "检定时间")
    private LocalDateTime calibrationTime;
    /**
     * 防盗扣编码
     */
    @ApiModelProperty(value = "防盗扣编码")
    @Length(max = 30, message = "防盗扣编码长度不能超过30")
    private String safeCode;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 20, message = "备注长度不能超过20")
    private String remark;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;
    /**
     * 删除标识 1-表示删除，0-正常使用
     */
    @ApiModelProperty(value = "删除标识 1-表示删除，0-正常使用")
    private Integer deleteStatus;
}
