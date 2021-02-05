package com.cdqckj.gmis.readmeter.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

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
@AllArgsConstructor
public class GasMeterBookRecordVo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    private Long readMeterBook;

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
     * 所属街道id
     */
    @ApiModelProperty(value = "所属街道id")
    private Long streetId;

    /**
     * 所属区域名称
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
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
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
    private String gasMeterNumber;

    /**
     * 气表类型
     */
    @ApiModelProperty(value = "气表类型")
    private String gasMeterType;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    private String useGasTypeName;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    private String gasMeterAddress;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String moreGasMeterAddress;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
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

}
