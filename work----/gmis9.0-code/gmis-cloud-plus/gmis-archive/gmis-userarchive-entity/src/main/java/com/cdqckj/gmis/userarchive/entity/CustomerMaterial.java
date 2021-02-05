package com.cdqckj.gmis.userarchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_customer_material")
@ApiModel(value = "CustomerMaterial", description = "")
@AllArgsConstructor
public class CustomerMaterial extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号")
    private String gasMeterNumber;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 32, message = "表具编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "表具编号")
    private String gasMeterCode;

    /**
     * ID：身份证件
     * CONTRACT：合同
     * OTHER:其他
     */
    @ApiModelProperty(value = "ID：身份证件")
    @Length(max = 10, message = "ID：身份证件长度不能超过10")
    @TableField(value = "material_type", condition = LIKE)
    @Excel(name = "ID：身份证件")
    private String materialType;

    /**
     * 附件表ID
     */
    @ApiModelProperty(value = "附件表ID")
    @TableField("material_attachment_id")
    @Excel(name = "附件表ID")
    private Long materialAttachmentId;

    /**
     * 材料地址
     */
    @ApiModelProperty(value = "材料地址")
    @Length(max = 1000, message = "材料地址长度不能超过1000")
    @TableField(value = "material_url", condition = LIKE)
    @Excel(name = "材料地址")
    private String materialUrl;

    /**
     * 缩略图地址
     */
    @ApiModelProperty(value = "缩略图地址")
    @Length(max = 1000, message = "缩略图地址长度不能超过1000")
    @TableField(value = "material_image_url", condition = LIKE)
    @Excel(name = "缩略图地址")
    private String materialImageUrl;

    /**
     * 原文件名
     */
    @ApiModelProperty(value = "原文件名")
    @Length(max = 200, message = "原文件名长度不能超过200")
    @TableField(value = "material_file_name", condition = LIKE)
    @Excel(name = "原文件名")
    private String materialFileName;

    /**
     * 原文件扩展名
     */
    @ApiModelProperty(value = "原文件扩展名")
    @Length(max = 10, message = "原文件扩展名长度不能超过10")
    @TableField(value = "material_file_extension", condition = LIKE)
    @Excel(name = "原文件扩展名")
    private String materialFileExtension;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;


    @Builder
    public CustomerMaterial(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                            String customerCode, String gasMeterNumber, String gasMeterCode, String materialType, Long materialAttachmentId,
                            String materialUrl, String materialImageUrl, String materialFileName, String materialFileExtension, Integer dataStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.customerCode = customerCode;
        this.gasMeterNumber = gasMeterNumber;
        this.gasMeterCode = gasMeterCode;
        this.materialType = materialType;
        this.materialAttachmentId = materialAttachmentId;
        this.materialUrl = materialUrl;
        this.materialImageUrl = materialImageUrl;
        this.materialFileName = materialFileName;
        this.materialFileExtension = materialFileExtension;
        this.dataStatus = dataStatus;
    }

}
