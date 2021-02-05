package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
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
 * 抄表册关联客户
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_gas_meter_book_record")
@ApiModel(value = "GasMeterBookRecord", description = "抄表册关联客户")
@AllArgsConstructor
public class GasMeterBookRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    @TableField(value = "read_meter_book", condition = LIKE)
    //@Excel(name = "抄表册id")
    private Long readMeterBook;

    @ApiModelProperty(value = "抄表编号")
    @TableField(value = "number", condition = LIKE)
    //@Excel(name = "抄表编号")
    private Integer number;

    /**
     * 所属区域id
     */
    @ApiModelProperty(value = "所属区域id")
    @TableField("community_id")
    //@Excel(name = "所属区域id")
    private Long communityId;

    /**
     * 所属区域名称
     */
    @ApiModelProperty(value = "所属区域名称")
    @Length(max = 100, message = "所属区域名称长度不能超过100")
    /*@TableField(value = "community_name", condition = LIKE)
    @Excel(name = "所属区域名称")*/
    private String communityName;

    /**
     * 所属街道id
     */
    @ApiModelProperty(value = "所属街道id")
    @TableField("street_id")
    //@Excel(name = "所属街道id")
    private Long streetId;

    /**
     * 所属区域名称
     */
    @ApiModelProperty(value = "所属街道名称")
    @Length(max = 100, message = "所属街道名称长度不能超过100")
    @TableField(value = "street_name", condition = LIKE)
    //@Excel(name = "所属街道名称")
    private String streetName;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @TableField("customer_id")
    //@Excel(name = "客户id")
    private Long customerId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 60, message = "客户名称长度不能超过60")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 60, message = "气表编号长度不能超过60")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Length(max = 32, message = "客户缴费编号长度不能超过32")
    //@TableField(value = "customer_charge_no", condition = LIKE)
    private String customerChargeNo;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 60, message = "表身号长度不能超过60")
    @TableField(value = "gas_meter_number", condition = LIKE)
    //@Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    @Length(max = 60, message = "气表类型长度不能超过60")
    @TableField(value = "gas_meter_type", condition = LIKE)
    @Excel(name = "气表类型")
    private String gasMeterType;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @Length(max = 30, message = "用气类型id长度不能超过30")
    @TableField(value = "use_gas_type_id", condition = LIKE)
    //@Excel(name = "用气类型id")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 60, message = "用气类型名称长度不能超过60")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 200, message = "安装地址长度不能超过200")
    @TableField(value = "more_gas_meter_address", condition = LIKE)
    @Excel(name = "安装地址")
    private String moreGasMeterAddress;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @Length(max = 32, message = "抄表员id长度不能超过32")
    @TableField(value = "read_meter_user", condition = LIKE)
    //@Excel(name = "抄表员id")
    private Long readMeterUser;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Length(max = 100, message = "抄表员名称长度不能超过100")
    @TableField(value = "read_meter_user_name", condition = LIKE)
    @Excel(name = "抄表员名称")
    @ExcelSelf(name = "抄表员名称,read Meter userName")
    private String readMeterUserName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("record_status")
    //@Excel(name = "状态")
    private Integer recordStatus;

    /**
     * 自定义序号
     */
    @ApiModelProperty(value = "自定义序号")
    @TableField("sort")
    @Excel(name = "自定义序号")
    private Integer sort;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 0：存在 1：删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Builder
    public GasMeterBookRecord(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime,Integer number,String customerChargeNo,
                              Long readMeterBook, Long customerId, String customerCode, String customerName, String gasMeterCode, String gasMeterType,
                    Long useGasTypeId, String useGasTypeName, String moreGasMeterAddress, Integer recordStatus, Integer sort,
                              Long communityId, String communityName, Long streetId, String streetName, Integer deleteStatus,
                              Long readMeterUser, String readMeterUserName, String gasMeterNumber) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.readMeterBook = readMeterBook;
        this.number = number;
        this.streetId = streetId;
        this.streetName = streetName;
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.customerChargeNo = customerChargeNo;
        this.gasMeterType = gasMeterType;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.moreGasMeterAddress = moreGasMeterAddress;
        this.recordStatus = recordStatus;
        this.sort = sort;
        this.communityId = communityId;
        this.communityName = communityName;
        this.readMeterUser = readMeterUser;
        this.readMeterUserName = readMeterUserName;
        this.gasMeterNumber = gasMeterNumber;
        this.deleteStatus = deleteStatus;
    }

}
