package com.cdqckj.gmis.installed.dto;

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
 * 报装资料
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasInstallFileSaveDTO", description = "报装资料")
public class GasInstallFileSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    private String installNumber;
    /**
     * ID：身份证件
     *             CONTRACT：合同
     *             OTHER:其他
     *             
     */
    @ApiModelProperty(value = "ID：身份证件")
    @Length(max = 10, message = "ID：身份证件长度不能超过10")
    private String materialType;
    /**
     * 材料地址
     */
    @ApiModelProperty(value = "材料地址")
    @Length(max = 1000, message = "材料地址长度不能超过1000")
    private String materialUrl;
    /**
     * 缩略图地址
     */
    @ApiModelProperty(value = "缩略图地址")
    @Length(max = 1000, message = "缩略图地址长度不能超过1000")
    private String materialImageUrl;
    /**
     * 原文件名
     */
    @ApiModelProperty(value = "原文件名")
    @Length(max = 200, message = "原文件名长度不能超过200")
    private String materialFileName;
    /**
     * 原文件扩展名
     */
    @ApiModelProperty(value = "原文件扩展名")
    @Length(max = 10, message = "原文件扩展名长度不能超过10")
    private String materialFileExtension;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

}
