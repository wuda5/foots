package com.cdqckj.gmis.userarchive.entity;

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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-06
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_purchase_limit_customer")
@ApiModel(value = "PurchaseLimitCustomer", description = "")
@AllArgsConstructor
public class PurchaseLimitCustomer extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 限购ID
     */
    @ApiModelProperty(value = "限购ID")
    @TableField("limit_id")
    @Excel(name = "限购ID")
    private Long limitId;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    @TableField("customer_code")
    @Excel(name = "客户ID")
    private String customerCode;

    /**
     * 气表ID
     */
    @ApiModelProperty(value = "气表ID")
    @TableField("gas_meter_code")
    @Excel(name = "气表ID")
    private String gasMeterCode;

    @ApiModelProperty(value = "删除状态（1-以删除，0-正常）")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Builder
    public PurchaseLimitCustomer(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    Long limitId, String customerCode, String gasMeterCode,Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.limitId = limitId;
        this.customerCode = customerCode;
        this.gasMeterCode = gasMeterCode;
        this.deleteStatus = deleteStatus;
    }

}
