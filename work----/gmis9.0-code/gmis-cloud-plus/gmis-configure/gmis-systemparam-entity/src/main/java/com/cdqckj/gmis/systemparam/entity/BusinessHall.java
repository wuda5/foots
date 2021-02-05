package com.cdqckj.gmis.systemparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
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
 * 营业厅信息表
 * </p>
 *
 * @author gmis
 * @since 2020-07-01
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_business_hall")
@ApiModel(value = "BusinessHall", description = "营业厅信息表")
@AllArgsConstructor
public class BusinessHall extends Entity<Long> {

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
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
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
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 营业厅地址
     */
    @ApiModelProperty(value = "营业厅地址")
    @Length(max = 100, message = "营业厅地址长度不能超过100")
    @TableField(value = "business_hall_address", condition = LIKE)
    @Excel(name = "营业厅地址")
    private String businessHallAddress;

    /**
     * 营业厅电话
     */
    @ApiModelProperty(value = "营业厅电话")
    @Length(max = 20, message = "营业厅电话不能超过20")
    @TableField(value = "business_hall_phone", condition = LIKE)
    @Excel(name = "营业厅电话")
    private String businessHallPhone;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 100, message = "联系人长度不能超过100")
    @TableField(value = "contacts", condition = LIKE)
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 联系邮件
     */
    @ApiModelProperty(value = "联系邮件")
    @Length(max = 30, message = "联系邮件长度不能超过30")
    @TableField(value = "email", condition = LIKE)
    @Excel(name = "联系邮件")
    private String email;

    /**
     * 配置类型
     */
    @ApiModelProperty(value = "配置类型")
    @Length(max = 30, message = "配置类型长度不能超过30")
    @TableField(value = "point_type", condition = LIKE)
    @Excel(name = "配置类型")
    private String pointType;

    /**
     * 代售点
     */
    @ApiModelProperty(value = "代售点")
    @TableField("is_sale")
    @Excel(name = "代售点")
    private Integer isSale;

    /**
     * 代售点（配额）
     */
    @ApiModelProperty(value = "代售点（配额）")
    @TableField("point_of_sale")
    @Excel(name = "代售点（配额）")
    private Integer pointOfSale;

    /**
     * 营业限制0：不限制，1：限制
     */
    @ApiModelProperty(value = "营业限制0：不限制，1：限制")
    @TableField("restrict_status")
    @Excel(name = "营业限制0：不限制，1：限制")
    private Integer restrictStatus;

    /**
     * 营业余额
     */
    @ApiModelProperty(value = "营业余额")
    @TableField("balance")
    @Excel(name = "营业余额")
    private BigDecimal balance;

    /**
     * 单笔限额
     */
    @ApiModelProperty(value = "单笔限额")
    @TableField("single_limit")
    @Excel(name = "单笔限额")
    private BigDecimal singleLimit;

    /**
     * 状态1：启用 0：停用
     */
    @ApiModelProperty(value = "状态1：启用 0：停用")
    @TableField("business_hall_status")
    @Excel(name = "状态1：启用 0：停用")
    private Integer businessHallStatus;

    /**
     * 删除人id
     */
    @ApiModelProperty(value = "删除人id")
    @TableField("delete_user")
    @Excel(name = "删除人id")
    private Long deleteUser;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 1：存在 0：删除")
    @TableField("delete_status")
    private Integer deleteStatus;


    @Builder
    public BusinessHall(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String businessHallName, Integer deleteStatus,
                    String businessHallAddress, String contacts, String telphone, String email, String pointType, Integer isSale, 
                    Integer pointOfSale, Integer restrictStatus, BigDecimal balance, BigDecimal singleLimit, Integer businessHallStatus, Long deleteUser, LocalDateTime deleteTime) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallName = businessHallName;
        this.businessHallAddress = businessHallAddress;
        this.contacts = contacts;
        this.telphone = telphone;
        this.email = email;
        this.pointType = pointType;
        this.isSale = isSale;
        this.pointOfSale = pointOfSale;
        this.restrictStatus = restrictStatus;
        this.balance = balance;
        this.singleLimit = singleLimit;
        this.businessHallStatus = businessHallStatus;
        this.deleteUser = deleteUser;
        this.deleteTime = deleteTime;
        this.deleteStatus = deleteStatus;
    }

}
