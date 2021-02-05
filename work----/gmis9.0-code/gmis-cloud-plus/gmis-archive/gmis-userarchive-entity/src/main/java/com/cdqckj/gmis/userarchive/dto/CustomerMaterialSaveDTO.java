package com.cdqckj.gmis.userarchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 *
 * </p>
 *
 * @author gmis
 * @since 2020-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerMaterialSaveDTO", description = "")
public class CustomerMaterialSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;
    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 32, message = "表具编号长度不能超过32")
    private String gasMeterCode;
    /**
     * ID：身份证件
     * CONTRACT：合同
     * OTHER:其他
     */
    @ApiModelProperty(value = "ID：身份证件")
    @Length(max = 10, message = "ID：身份证件长度不能超过10")
    private String materialType;
    /**
     * 附件表ID
     */
    @ApiModelProperty(value = "附件表ID")
    private Long materialAttachmentId;
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
    private Integer dataStatus;

}
