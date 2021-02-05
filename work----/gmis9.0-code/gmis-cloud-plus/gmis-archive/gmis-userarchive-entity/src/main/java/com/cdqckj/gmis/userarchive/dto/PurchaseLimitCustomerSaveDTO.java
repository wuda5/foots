package com.cdqckj.gmis.userarchive.dto;

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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PurchaseLimitCustomerSaveDTO", description = "")
public class PurchaseLimitCustomerSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 限购ID
     */
    @ApiModelProperty(value = "限购ID")
    private Long limitId;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private String customerCode;
    /**
     * 气表ID
     */
    @ApiModelProperty(value = "气表ID")
    private String gasMeterCode;

    @ApiModelProperty(value = "删除状态（1-以删除，0-正常）")
    private Integer deleteStatus;
}
