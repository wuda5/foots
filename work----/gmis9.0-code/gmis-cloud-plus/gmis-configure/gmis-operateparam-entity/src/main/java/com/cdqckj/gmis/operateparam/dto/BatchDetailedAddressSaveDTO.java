package com.cdqckj.gmis.operateparam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value = "DetailedAddressSaveDTO", description = "")
public class BatchDetailedAddressSaveDTO extends BaseAddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "街道id",required = true)
    private Long streetId;

    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id：为空及表示新增小区 ")
    private Long communityId;

    @ApiModelProperty(value = "小区名字")
    private String communityName;
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

    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    private Integer deleteStatus;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deleteUser;

    @ApiModelProperty(value = "楼层")
    private Integer storey;

    @ApiModelProperty(value = "户数")
    private Integer households;

    @ApiModelProperty(value = "调压箱数")
    private Integer nodeCount;

    @ApiModelProperty(value = "是否调压箱数配置",required = true)
    private Boolean nodeFlag;
}
