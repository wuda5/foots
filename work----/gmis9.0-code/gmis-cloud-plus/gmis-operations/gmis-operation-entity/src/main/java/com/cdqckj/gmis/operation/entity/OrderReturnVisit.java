package com.cdqckj.gmis.operation.entity;

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
 * 工单回访记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_order_return_visit")
@ApiModel(value = "OrderReturnVisit", description = "工单回访记录")
@AllArgsConstructor
public class OrderReturnVisit extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    @TableField("GC__id")
    @Excel(name = "")
    private Long gcId;

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

    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    @TableField(value = "business_hall_id", condition = LIKE)
    @Excel(name = "")
    private String businessHallId;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "")
    private String businessHallName;

    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "")
    private String customerCode;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "")
    private String customerName;

    @ApiModelProperty(value = "")
    @Length(max = 11, message = "长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "")
    private String telphone;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "")
    private String gasMeterCode;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "")
    private String gasMeterName;

    @ApiModelProperty(value = "")
    @TableField("gas_meter_type_id")
    @Excel(name = "")
    private Long gasMeterTypeId;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "")
    private String gasMeterTypeName;

    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    @TableField(value = "job_number", condition = LIKE)
    @Excel(name = "")
    private String jobNumber;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "satisfaction", condition = LIKE)
    @Excel(name = "")
    private String satisfaction;

    @ApiModelProperty(value = "")
    @Length(max = 300, message = "长度不能超过300")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "")
    private String remark;

    @ApiModelProperty(value = "")
    @TableField("data_status")
    @Excel(name = "")
    private Integer dataStatus;

    @ApiModelProperty(value = "")
    @TableField("create_user_id")
    @Excel(name = "")
    private Long createUserId;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "")
    private String createUserName;


    @Builder
    public OrderReturnVisit(Long id, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    Long gcId, String companyCode, String companyName, Long orgId, String orgName, 
                    String businessHallId, String businessHallName, String customerCode, String customerName, String telphone, String gasMeterCode, 
                    String gasMeterName, Long gasMeterTypeId, String gasMeterTypeName, String jobNumber, String satisfaction, String remark, 
                    Integer dataStatus, Long createUserId, String createUserName) {
        this.id = id;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.gcId = gcId;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.telphone = telphone;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterName = gasMeterName;
        this.gasMeterTypeId = gasMeterTypeId;
        this.gasMeterTypeName = gasMeterTypeName;
        this.jobNumber = jobNumber;
        this.satisfaction = satisfaction;
        this.remark = remark;
        this.dataStatus = dataStatus;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
    }

}
