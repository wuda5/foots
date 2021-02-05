package com.cdqckj.gmis.installed.entity;

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
 * 报装资料
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_gas_install_file")
@ApiModel(value = "GasInstallFile", description = "报装资料")
@AllArgsConstructor
public class GasInstallFile extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    @TableField(value = "install_number", condition = LIKE)
    @Excel(name = "报装编号")
    private String installNumber;

    /**
     * ID：身份证件
     *             CONTRACT：合同
     *             OTHER:其他
     *             
     */
    @ApiModelProperty(value = "ID：身份证件")
    @Length(max = 10, message = "ID：身份证件长度不能超过10")
    @TableField(value = "material_type", condition = LIKE)
    @Excel(name = "ID：身份证件")
    private String materialType;

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
    @TableField("status")
    @Excel(name = "状态")
    private Integer status;


    @Builder
    public GasInstallFile(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String installNumber, String materialType, String materialUrl, String materialImageUrl, String materialFileName, 
                    String materialFileExtension, Integer status) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.installNumber = installNumber;
        this.materialType = materialType;
        this.materialUrl = materialUrl;
        this.materialImageUrl = materialImageUrl;
        this.materialFileName = materialFileName;
        this.materialFileExtension = materialFileExtension;
        this.status = status;
    }

}
