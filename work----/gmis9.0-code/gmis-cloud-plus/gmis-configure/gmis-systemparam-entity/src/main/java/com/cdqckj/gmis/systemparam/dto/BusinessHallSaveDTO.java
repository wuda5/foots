package com.cdqckj.gmis.systemparam.dto;

import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 营业厅信息表
 * </p>
 *
 * @author gmis
 * @since 2020-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "BusinessHallSaveDTO", description = "营业厅信息表")
public class BusinessHallSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 营业厅地址
     */
    @ApiModelProperty(value = "营业厅地址")
    @Length(max = 100, message = "营业厅地址长度不能超过100")
    private String businessHallAddress;
    /**
     * 营业厅电话
     */
    @ApiModelProperty(value = "营业厅电话")
    @Length(max = 20, message = "营业厅电话不能超过20")
    private String businessHallPhone;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 100, message = "联系人长度不能超过100")
    private String contacts;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    private String telphone;
    /**
     * 联系邮件
     */
    @ApiModelProperty(value = "联系邮件")
    @Length(max = 30, message = "联系邮件长度不能超过30")
    private String email;
    /**
     * 配置类型
     */
    @ApiModelProperty(value = "配置类型")
    @Length(max = 30, message = "配置类型长度不能超过30")
    private String pointType;
    /**
     * 代售点
     */
    @ApiModelProperty(value = "代售点")
    private Integer isSale;
    /**
     * 代售点（配额）
     */
    @ApiModelProperty(value = "代售点（配额）")
    private Integer pointOfSale;
    /**
     * 营业限制0：不限制，1：限制
     */
    @ApiModelProperty(value = "营业限制0：不限制，1：限制")
    private Integer restrictStatus;
    /**
     * 营业余额
     */
    @ApiModelProperty(value = "营业余额")
    private BigDecimal balance;
    /**
     * 单笔限额
     */
    @ApiModelProperty(value = "单笔限额")
    private BigDecimal singleLimit;
    /**
     * 状态1：启用 0：停用
     */
    @ApiModelProperty(value = "状态1：启用 0：停用")
    private Integer businessHallStatus;
    /**
     * 删除人id
     */
    @ApiModelProperty(value = "删除人id")
    private Long deleteUser;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除标识1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识1：存在 0：删除")
    private Integer deleteStatus;

}
