package com.cdqckj.gmis.tenant.entity;

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
 * 支付宝微信-生活缴费关联租户
 * </p>
 *
 * @author gmis
 * @since 2021-01-22
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_living_expenses")
@ApiModel(value = "LivingExpenses", description = "支付宝微信-生活缴费关联租户")
@AllArgsConstructor
public class LivingExpenses extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    @NotEmpty(message = "租户编码不能为空")
    @Length(max = 30, message = "租户编码长度不能超过30")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "租户编码")
    private String companyCode;

    /**
     * 支付宝清算单位
     */
    @ApiModelProperty(value = "支付宝清算单位")
    @Length(max = 20, message = "支付宝清算单位长度不能超过20")
    @TableField(value = "alipay_code", condition = LIKE)
    @Excel(name = "支付宝清算单位")
    private String alipayCode;

    /**
     * 微信清算单位
     */
    @ApiModelProperty(value = "微信清算单位")
    @Length(max = 100, message = "微信清算单位长度不能超过100")
    @TableField(value = "weixin_code", condition = LIKE)
    @Excel(name = "微信清算单位")
    private String weixinCode;

    /**
     * 删除标识（0：存在；1：删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1：删除）")
    @TableField("delete_status")
    @Excel(name = "删除标识（0：存在；1：删除）")
    private Integer deleteStatus;


    @Builder
    public LivingExpenses(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String alipayCode, String weixinCode, Integer deleteStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.alipayCode = alipayCode;
        this.weixinCode = weixinCode;
        this.deleteStatus = deleteStatus;
    }

}
