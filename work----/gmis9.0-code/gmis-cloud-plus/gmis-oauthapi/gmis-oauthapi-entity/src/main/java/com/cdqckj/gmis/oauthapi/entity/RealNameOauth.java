package com.cdqckj.gmis.oauthapi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
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
 * app应用实名认证表：租户客户关联表
 * </p>
 *
 * @author gmis
 * @since 2020-10-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gl_real_name_oauth")
@ApiModel(value = "RealNameOauth", description = "app应用实名认证表：租户客户关联表")
@AllArgsConstructor
public class RealNameOauth extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    @Length(max = 36, message = "应用id长度不能超过36")
    @TableField(value = "app_id", condition = LIKE)
    @Excel(name = "应用id")
    private String appId;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    @TableField("customer_id")
    @Excel(name = "客户id")
    private Long customerId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 租户code
     */
    @ApiModelProperty(value = "租户code")
    @TableField(value = "tenant_code", condition = LIKE)
    @Excel(name = "燃气公司")
    private String tenantCode;

    /**
     * 燃气公司名称
     */
    @ApiModelProperty(value = "燃气公司名称")
    @Length(max = 255, message = "燃气公司名称长度不能超过255")
    @TableField(value = "tenant_name", condition = LIKE)
    @Excel(name = "燃气公司名称")
    private String tenantName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Length(max = 12, message = "手机号码长度不能超过12")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "手机号码")
    private String telphone;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @Length(max = 20, message = "身份证号长度不能超过20")
    @TableField(value = "id_card", condition = LIKE)
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 绑定状态1、绑定中；0、解绑
     */
    @ApiModelProperty(value = "绑定状态1、绑定中；0、解绑")
    @TableField("bind_status")
    @Excel(name = "绑定状态1、绑定中；0、解绑")
    private Integer bindStatus;

    /**
     * 数据状态1、正常；0、删除
     */
    @ApiModelProperty(value = "数据状态1、正常；0、删除")
    @TableField("delete_status")
    @Excel(name = "数据状态1、正常；0、删除")
    private Integer deleteStatus;

    /**
     * 扩展字段1
     */
    @ApiModelProperty(value = "扩展字段1")
    @Length(max = 255, message = "扩展字段1长度不能超过255")
    @TableField(value = "str1", condition = LIKE)
    @Excel(name = "扩展字段1")
    private String str1;

    /**
     * 扩展字段2
     */
    @ApiModelProperty(value = "扩展字段2")
    @Length(max = 255, message = "扩展字段2长度不能超过255")
    @TableField(value = "shr2", condition = LIKE)
    @Excel(name = "扩展字段2")
    private String shr2;


    @Builder
    public RealNameOauth(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String appId, Long customerId, String customerName, String customerCode, String tenantName, 
                    String telphone, String idCard, Integer bindStatus, Integer deleteStatus, String str1, String shr2) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.appId = appId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.tenantName = tenantName;
        this.telphone = telphone;
        this.idCard = idCard;
        this.bindStatus = bindStatus;
        this.deleteStatus = deleteStatus;
        this.str1 = str1;
        this.shr2 = shr2;
    }

}
