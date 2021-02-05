package com.cdqckj.gmis.operation.dto;

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
 * 工单现场资料
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
@ApiModel(value = "OrderJobFileSaveDTO", description = "工单现场资料")
public class OrderJobFileSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单id,必须传入正确的")
    private Long jobId;
    /**
     * ID：身份证件
     * CONTRACT：合同
     * OTHER:其他
     */
    @ApiModelProperty(value = "材料类型 ID：身份证件")
    @Length(max = 10, message = "ID：身份证件长度不能超过10")
    private String materialType;

    @ApiModelProperty(value = "材料地址")
    @Length(max = 1000, message = "长度不能超过1000")
    private String materialUrl;

    @ApiModelProperty(value = "缩略图地址")
    @Length(max = 1000, message = "长度不能超过1000")
    private String materialImageUrl;

    @ApiModelProperty(value = "原文件名")
    @Length(max = 200, message = "长度不能超过200")
    private String materialFileName;

    @ApiModelProperty(value = "原文件扩展名")
    @Length(max = 10, message = "长度不能超过10")

    private String materialFileExtension;
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

}
