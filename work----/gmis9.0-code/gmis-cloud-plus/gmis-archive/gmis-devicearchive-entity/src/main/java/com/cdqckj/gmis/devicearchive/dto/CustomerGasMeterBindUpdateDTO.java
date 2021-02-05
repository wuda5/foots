package com.cdqckj.gmis.devicearchive.dto;

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
 * 客户气表绑定关系表 - 代缴业务
 * </p>
 *
 * @author wanghuaqiao
 * @since 2020-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerGasMeterBindUpdateDTO", description = "客户气表绑定关系表 - 代缴业务")
public class CustomerGasMeterBindUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 客户档案编号
     */
    @ApiModelProperty(value = "客户档案编号")
    @Length(max = 32, message = "客户档案编号长度不能超过32")
    private String customerCode;
    /**
     * 绑定的其他客户档案编号
     */
    @ApiModelProperty(value = "绑定的其他客户档案编号")
    @Length(max = 32, message = "绑定的其他客户档案编号长度不能超过32")
    private String bindCustomerCode;
    /**
     * 绑定的其他客户的气表档案编号
     */
    @ApiModelProperty(value = "绑定的其他客户的气表档案编号")
    @Length(max = 32, message = "绑定的其他客户的气表档案编号长度不能超过32")
    private String bindGasMeterCode;
    /**
     * 绑定状态：1、绑定  0、解绑
     */
    @ApiModelProperty(value = "绑定状态：1、绑定  0、解绑")
    private Boolean bindStatus;
}
