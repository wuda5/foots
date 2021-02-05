package com.cdqckj.gmis.devicearchive.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.devicearchive.enumeration.RelateLevelType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerGasMeterRelatedUpdateDTO", description = "")
public class CustomerGasMeterRelatedUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;


    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;

    /**
     * 客户档案ID
     */
    /**
     * 客户档案ID
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 气表档案ID
     */
    @ApiModelProperty(value = "气表编号")
    private String gasMeterCode;


    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    /**
     * 客户缴费编号标识 : 1自增,0人工录入
     */
    @ApiModelProperty(value = "客户缴费编号标识:1自增,0人工录入")
    private Integer customerChargeFlag;

    /**
     * 
     * #RelateLevelType{MAIN_HOUSEHOLD:主户;QUOTE:引用;TENANT:租户;}
     */
    @ApiModelProperty(value = "")
    private RelateLevelType relatedLevel;
    /**
     * 抄表抵扣或银行代扣
     */
    @ApiModelProperty(value = "抄表抵扣或银行代扣")
    private Integer relatedDeductions;
    /**
     * 客户表具关系操作类型
     * 开户: OPEN_ACCOUNT
     * 过户: TRANS_ACCOUNT
     * 销户: DIS_ACCOUNT
     * 换表: CHANGE_METER
     */
    @ApiModelProperty(value = "客户表具关系操作类型")
    private String operType;

    /**
     * 数据状态  1 有效 0无效
     */
    @ApiModelProperty(value = "数据状态 1 有效 0无效")
    private Integer dataStatus;
}
