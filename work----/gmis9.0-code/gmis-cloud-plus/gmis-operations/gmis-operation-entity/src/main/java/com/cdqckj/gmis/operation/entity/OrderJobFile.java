package com.cdqckj.gmis.operation.entity;

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
 * 工单现场资料
 * </p>
 *
 * @author gmis
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_order_job_file")
@ApiModel(value = "OrderJobFile", description = "工单现场资料")
@AllArgsConstructor
public class OrderJobFile extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单id")
    @TableField("job_id")
    @Excel(name = "")
    private Long jobId;

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

    @ApiModelProperty(value = "")
    @Length(max = 1000, message = "长度不能超过1000")
    @TableField(value = "material_url", condition = LIKE)
    @Excel(name = "")
    private String materialUrl;

    @ApiModelProperty(value = "")
    @Length(max = 1000, message = "长度不能超过1000")
    @TableField(value = "material_image_url", condition = LIKE)
    @Excel(name = "")
    private String materialImageUrl;

    @ApiModelProperty(value = "")
    @Length(max = 200, message = "长度不能超过200")
    @TableField(value = "material_file_name", condition = LIKE)
    @Excel(name = "")
    private String materialFileName;

    @ApiModelProperty(value = "")
    @Length(max = 10, message = "长度不能超过10")
    @TableField(value = "material_file_extension", condition = LIKE)
    @Excel(name = "")
    private String materialFileExtension;

    @ApiModelProperty(value = "")
    @TableField("data_status")
    @Excel(name = "")
    private Integer dataStatus;


    @Builder
    public OrderJobFile(Long id, 
                    Long jobId, String materialType, String materialUrl, String materialImageUrl, String materialFileName, 
                    String materialFileExtension, Integer dataStatus) {
        this.id = id;
        this.jobId = jobId;
        this.materialType = materialType;
        this.materialUrl = materialUrl;
        this.materialImageUrl = materialImageUrl;
        this.materialFileName = materialFileName;
        this.materialFileExtension = materialFileExtension;
        this.dataStatus = dataStatus;
    }

}
