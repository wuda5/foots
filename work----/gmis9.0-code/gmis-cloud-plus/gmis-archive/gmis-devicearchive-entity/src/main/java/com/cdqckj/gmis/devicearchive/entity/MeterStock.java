package com.cdqckj.gmis.devicearchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * @since 2020-07-09
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_meter_stock")
@ApiModel(value = "MeterStock", description = "")
@AllArgsConstructor
public class MeterStock extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 30, message = "气表编号长度不能超过30")
    @TableField(value = "gas_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasCode;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @TableField("gas_meter_factory_id")
    @Excel(name = "厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String gasMeterFactoryName;

    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
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
     * 表类型
     */
    @ApiModelProperty(value = "表类型")
    @TableField("gas_meter_type_id")
    @Excel(name = "表类型")
    private Long gasMeterTypeId;

    /**
     * 表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）
     */
    @ApiModelProperty(value = "表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）")
    @Length(max = 30, message = "表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）长度不能超过30")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "表类型名称 普表 卡表 物联网表 流量计(普表) 流量计(卡表）流量计(物联网表）")
    private String gasMeterTypeName;

    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间")
    @TableField("buy_gas")
    @Excel(name = "购买时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime buyGas;

    /**
     * 检定时间
     */
    @ApiModelProperty(value = "检定时间")
    @TableField("calibration_time")
    @Excel(name = "检定时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime calibrationTime;

    /**
     * 防盗扣编码
     */
    @ApiModelProperty(value = "防盗扣编码")
    @Length(max = 30, message = "防盗扣编码长度不能超过30")
    @TableField(value = "safe_code", condition = LIKE)
    @Excel(name = "防盗扣编码")
    private String safeCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 20, message = "备注长度不能超过20")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;

    /**
     * 删除标识 1-表示删除，0-正常使用
     */
    @ApiModelProperty(value = "删除标识 1-表示删除，0-正常使用")
    @TableField("delete_status")
    @Excel(name = "删除标识 1-表示删除，0-正常使用")
    private Integer deleteStatus;


    @Builder
    public MeterStock(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String gasCode, 
                    Long gasMeterFactoryId, String gasMeterFactoryName, Long gasMeterVersionId, String gasMeterVersionName, Long gasMeterTypeId, String gasMeterTypeName, 
                    LocalDateTime buyGas, LocalDateTime calibrationTime, String safeCode, String remark, Integer dataStatus, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.gasCode = gasCode;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.gasMeterTypeId = gasMeterTypeId;
        this.gasMeterTypeName = gasMeterTypeName;
        this.buyGas = buyGas;
        this.calibrationTime = calibrationTime;
        this.safeCode = safeCode;
        this.remark = remark;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
    }

}
