package com.cdqckj.gmis.readmeter.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 抄表册关联客户
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterBookRecordSaveDTO", description = "抄表册关联客户")
public class GasMeterBookRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    private Long readMeterBook;
    /**
     * 抄表编号
     */
    @ApiModelProperty(value = "抄表编号")
    private Integer number;
    /**
     * 所属区域id
     */
    @ApiModelProperty(value = "所属区域id")
    private Long communityId;

    /**
     * 所属区域名称
     */
    @ApiModelProperty(value = "所属区域名称")
    private String communityName;
    /**
     * 所属街道d
     */
    @ApiModelProperty(value = "所属街道id")
    private Long streetId;

    /**
     * 所属街道名称
     */
    @ApiModelProperty(value = "所属街道名称")
    private String streetName;
    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long customerId;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 60, message = "客户名称长度不能超过60")
    private String customerName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 60, message = "气表编号长度不能超过60")
    private String gasMeterCode;
    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 60, message = "表身号长度不能超过60")
    private String gasMeterNumber;
    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    @Length(max = 60, message = "气表类型长度不能超过60")
    private String gasMeterType;
    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    @Length(max = 30, message = "用气类型id长度不能超过30")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 60, message = "用气类型名称长度不能超过60")
    private String useGasTypeName;
    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 200, message = "安装地址长度不能超过200")
    private String moreGasMeterAddress;
    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    //@Excel(name = "抄表员id")
    private Long readMeterUser;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    private String readMeterUserName;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer recordStatus;
    /**
     * 自定义序号
     */
    @ApiModelProperty(value = "自定义序号")
    private Integer sort;

    @ApiModelProperty(value = "删除标识 0：存在 1：删除")
    private Integer deleteStatus;

}
