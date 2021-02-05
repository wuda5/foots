package com.cdqckj.gmis.oauthapi.dto;

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
import com.cdqckj.gmis.base.entity.SuperEntity;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "RealNameOauthUpdateDTO", description = "app应用实名认证表：租户客户关联表")
public class RealNameOauthUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    @Length(max = 36, message = "应用id长度不能超过36")
    private String appId;
    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    /**
     * 燃气公司名称
     */
    @ApiModelProperty(value = "燃气公司名称")
    @Length(max = 255, message = "燃气公司名称长度不能超过255")
    private String tenantName;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Length(max = 12, message = "手机号码长度不能超过12")
    private String telphone;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @Length(max = 20, message = "身份证号长度不能超过20")
    private String idCard;
    /**
     * 绑定状态1、绑定中；0、解绑
     */
    @ApiModelProperty(value = "绑定状态1、绑定中；0、解绑")
    private Integer bindStatus;
    /**
     * 数据状态1、正常；0、删除
     */
    @ApiModelProperty(value = "数据状态1、正常；0、删除")
    private Integer deleteStatus;
    /**
     * 扩展字段1
     */
    @ApiModelProperty(value = "扩展字段1")
    @Length(max = 255, message = "扩展字段1长度不能超过255")
    private String str1;
    /**
     * 扩展字段2
     */
    @ApiModelProperty(value = "扩展字段2")
    @Length(max = 255, message = "扩展字段2长度不能超过255")
    private String shr2;
}
