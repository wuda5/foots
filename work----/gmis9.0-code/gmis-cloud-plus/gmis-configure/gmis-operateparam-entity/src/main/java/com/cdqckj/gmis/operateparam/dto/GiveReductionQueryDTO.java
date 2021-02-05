package com.cdqckj.gmis.operateparam.dto;

import com.cdqckj.gmis.operateparam.enumeration.ActivityScene;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 赠送减免活动配置
 * </p>
 *
 * @author gmis
 * @since 2020-08-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GiveReductionConfSaveDTO", description = "赠送减免活动配置")
public class GiveReductionQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;


//    /**
//     * 开始时间
//     */
//    @ApiModelProperty(value = "当前时间")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate nowDate;

    /**
     * 活动场景
     */
    @ApiModelProperty(value = "活动场景")
    private ActivityScene activityScene;

    /**
     * 收费项ID集合
     */
    @ApiModelProperty(value = "收费项ID集合")
    private List<Long> tollItemIds;


    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;

//    /**
//     * 是否启用
//     *             1 启用
//     *             0 不启用
//     */
//    @ApiModelProperty(value = "是否启用")
//    private Integer dataStatus;
//    /**
//     * 删除状态
//     *             1 删除
//     *             0 正常
//     */
//    @ApiModelProperty(value = "删除状态")
//    private Integer deleteStatus;

}
