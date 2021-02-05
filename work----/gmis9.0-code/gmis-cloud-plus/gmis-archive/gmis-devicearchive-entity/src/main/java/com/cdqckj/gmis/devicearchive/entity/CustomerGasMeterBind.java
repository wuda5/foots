package com.cdqckj.gmis.devicearchive.entity;

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
 * 客户气表绑定关系表 - 代缴业务
 * </p>
 *
 * @author wanghuaqiao
 * @since 2020-10-16
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_customer_gas_meter_bind")
@ApiModel(value = "CustomerGasMeterBind", description = "客户气表绑定关系表 - 代缴业务")
@AllArgsConstructor
public class CustomerGasMeterBind extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户档案编号
     */
    @ApiModelProperty(value = "客户档案编号")
    @Length(max = 32, message = "客户档案编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户档案编号")
    private String customerCode;

    /**
     * 绑定的其他客户档案编号
     */
    @ApiModelProperty(value = "绑定的其他客户档案编号")
    @Length(max = 32, message = "绑定的其他客户档案编号长度不能超过32")
    @TableField(value = "bind_customer_code", condition = LIKE)
    @Excel(name = "绑定的其他客户档案编号")
    private String bindCustomerCode;

    /**
     * 绑定的其他客户的气表档案编号
     */
    @ApiModelProperty(value = "绑定的其他客户的气表档案编号")
    @Length(max = 32, message = "绑定的其他客户的气表档案编号长度不能超过32")
    @TableField(value = "bind_gas_meter_code", condition = LIKE)
    @Excel(name = "绑定的其他客户的气表档案编号")
    private String bindGasMeterCode;

    /**
     * 绑定状态：1、绑定  0、解绑
     */
    @ApiModelProperty(value = "绑定状态：1、绑定  0、解绑")
    @TableField("bind_status")
    @Excel(name = "绑定状态：1、绑定  0、解绑", replace = {"是_true", "否_false", "_null"})
    private Boolean bindStatus;


    @Builder
    public CustomerGasMeterBind(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String customerCode, String bindCustomerCode, String bindGasMeterCode, Boolean bindStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.customerCode = customerCode;
        this.bindCustomerCode = bindCustomerCode;
        this.bindGasMeterCode = bindGasMeterCode;
        this.bindStatus = bindStatus;
    }

}
