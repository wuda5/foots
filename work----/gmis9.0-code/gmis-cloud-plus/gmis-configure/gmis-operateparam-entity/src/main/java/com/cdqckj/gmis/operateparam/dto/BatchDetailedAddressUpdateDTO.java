package com.cdqckj.gmis.operateparam.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 
 * </p>
 *
 * @author 王华侨
 * @since 2020-09-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "DetailedAddressUpdateDTO", description = "")
public class BatchDetailedAddressUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    private Long communityId;
    /**
     * 栋
     */
    @ApiModelProperty(value = "栋")
    private Integer building;
    /**
     * 单元
     */
    @ApiModelProperty(value = "单元")
    private Integer unit;
    /**
     * 详细地址（不包含栋，单元的地址）
     */
    @ApiModelProperty(value = "详细地址（不包含栋，单元的地址）")
    @Length(max = 200, message = "详细地址（不包含栋，单元的地址）长度不能超过200")
    private String detailAddress;
    /**
     * 包括栋单元的详细地址
     */
    @ApiModelProperty(value = "包括栋单元的详细地址")
    @Length(max = 200, message = "包括栋单元的详细地址长度不能超过200")
    private String moreDetailAddress;
    /**
     * 0表示农村，1表示城市
     */
    @ApiModelProperty(value = "0表示农村，1表示城市")
    private Integer flag;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deleteUser;

    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    private Integer deleteStatus;
}
