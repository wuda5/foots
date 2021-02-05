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
@ApiModel(value = "OrderJobFileUpdateDTO", description = "工单现场资料")
public class OrderJobFileUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    @ApiModelProperty(value = "")
    private Long jobId;
    /**
     * ID：身份证件
     *             CONTRACT：合同
     *             OTHER:其他
     *             
     */
    @ApiModelProperty(value = "ID：身份证件")
    @Length(max = 10, message = "ID：身份证件长度不能超过10")
    private String materialType;
    @ApiModelProperty(value = "")
    @Length(max = 1000, message = "长度不能超过1000")
    private String materialUrl;
    @ApiModelProperty(value = "")
    @Length(max = 1000, message = "长度不能超过1000")
    private String materialImageUrl;
    @ApiModelProperty(value = "")
    @Length(max = 200, message = "长度不能超过200")
    private String materialFileName;
    @ApiModelProperty(value = "")
    @Length(max = 10, message = "长度不能超过10")
    private String materialFileExtension;
    @ApiModelProperty(value = "")
    private Integer dataStatus;
}
