package com.cdqckj.gmis.userarchive.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerDesVo", description = "客户列表查询参数")
public class CustomerBlackListVo {
    private Integer pageNo;
    private  Integer pageSize;

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
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型ID")
    @TableField("customer_type_id")
    @Excel(name = "客户类型ID")
    private Long customerTypeId;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField("sex")
    @Excel(name = "性别")
    private Integer sex;

    /**
     * 证件类型ID
     */
    @ApiModelProperty(value = "证件类型ID")
    @Length(max = 10, message = "证件类型ID长度不能超过10")
    @TableField(value = "id_type_id", condition = LIKE)
    @Excel(name = "证件类型ID")
    private String idTypeId;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @Length(max = 30, message = "证件号码长度不能超过30")
    @TableField(value = "id_number", condition = LIKE)
    @Excel(name = "证件号码")
    private String idNumber;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 家庭住址/单位地址
     */
    @ApiModelProperty(value = "家庭住址/单位地址")
    @Length(max = 100, message = "家庭住址/单位地址长度不能超过100")
    @TableField(value = "contact_address", condition = LIKE)
    @Excel(name = "家庭住址/单位地址")
    private String contactAddress;
    /**
     * 客户状态 预建档 有效 销户
     */
    @ApiModelProperty(value = "客户状态 预建档 有效 销户")
    @TableField("customer_status")
    @Excel(name = "客户状态 预建档 有效 销户")
    private Integer customerStatus;

}
