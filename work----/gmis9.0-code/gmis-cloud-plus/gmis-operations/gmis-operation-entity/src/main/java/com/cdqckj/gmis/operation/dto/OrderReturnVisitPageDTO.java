package com.cdqckj.gmis.operation.dto;

import java.time.LocalDateTime;
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
 * 工单回访记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "OrderReturnVisitPageDTO", description = "工单回访记录")
public class OrderReturnVisitPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Long gcId;
    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    private String companyCode;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String companyName;
    @ApiModelProperty(value = "")
    private Long orgId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String orgName;
    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    private String businessHallId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String businessHallName;
    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    private String customerCode;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String customerName;
    @ApiModelProperty(value = "")
    @Length(max = 11, message = "长度不能超过11")
    private String telphone;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String gasMeterCode;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String gasMeterName;
    @ApiModelProperty(value = "")
    private Long gasMeterTypeId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String gasMeterTypeName;
    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    private String jobNumber;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String satisfaction;
    @ApiModelProperty(value = "")
    @Length(max = 300, message = "长度不能超过300")
    private String remark;
    @ApiModelProperty(value = "")
    private Integer dataStatus;
    @ApiModelProperty(value = "")
    private Long createUserId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String createUserName;

}
