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
 * @since 2020-08-17
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_input_output_meter_story")
@ApiModel(value = "InputOutputMeterStory", description = "")
@AllArgsConstructor
public class InputOutputMeterStory extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "")
    private String companyCode;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "")
    private String companyName;

    @ApiModelProperty(value = "")
    @TableField("org_id")
    @Excel(name = "")
    private Long orgId;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "")
    private String orgName;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    @Length(max = 100, message = "批次号长度不能超过100")
    @TableField(value = "serial_code", condition = LIKE)
    @Excel(name = "批次号")
    private String serialCode;

    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    @TableField(value = "gas_meter_factory_id", condition = LIKE)
    @Excel(name = "")
    private String gasMeterFactoryId;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
    @Excel(name = "")
    private String gasMeterFactoryName;

    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @Length(max = 32, message = "版号id长度不能超过32")
    @TableField(value = "gas_meter_version_id", condition = LIKE)
    @Excel(name = "版号id")
    private String gasMeterVersionId;

    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    @TableField(value = "gas_meter_version_name", condition = LIKE)
    @Excel(name = "版号名称")
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
    @TableField(value = "gas_meter_type_id", condition = LIKE)
    @Excel(name = "普表")
    private String gasMeterTypeId;

    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "")
    private String gasMeterTypeName;

    /**
     * 1表示出库
     * 			0表示入库
     * 		
     */
    @ApiModelProperty(value = "1表示出库")
    @TableField("status")
    @Excel(name = "1表示出库")
    private Integer status;

    /**
     * 表示库存数量
     */
    @ApiModelProperty(value = "表示库存数量")
    @TableField("store_count")
    @Excel(name = "表示库存数量")
    private Long storeCount;


    @Builder
    public InputOutputMeterStory(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String serialCode, 
                    String gasMeterFactoryId, String gasMeterFactoryName, String gasMeterVersionId, String gasMeterVersionName, String gasMeterTypeId, String gasMeterTypeName, 
                    Integer status, Long storeCount) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.serialCode = serialCode;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterVersionId = gasMeterVersionId;
        this.gasMeterVersionName = gasMeterVersionName;
        this.gasMeterTypeId = gasMeterTypeId;
        this.gasMeterTypeName = gasMeterTypeName;
        this.status = status;
        this.storeCount = storeCount;
    }

}
