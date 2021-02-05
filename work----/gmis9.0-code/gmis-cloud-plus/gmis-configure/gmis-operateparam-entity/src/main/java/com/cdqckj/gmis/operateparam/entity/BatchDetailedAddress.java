package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 
 * </p>
 *
 * @author 王华侨
 * @since 2020-09-18
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_detailed_address")
@ApiModel(value = "BatchDetailedAddress", description = "")
@AllArgsConstructor
public class BatchDetailedAddress extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    @TableField("community_id")
    @Excel(name = "小区id")
    private Long communityId;

    /**
     * 栋
     */
    @ApiModelProperty(value = "栋")
    @TableField("building")
    @Excel(name = "栋")
    private Integer building;

    /**
     * 单元
     */
    @ApiModelProperty(value = "单元")
    @TableField("unit")
    @Excel(name = "单元")
    private Integer unit;

    /**
     * 详细地址（不包含栋，单元的地址）
     */
    @ApiModelProperty(value = "详细地址（不包含栋，单元的地址）")
    @Length(max = 200, message = "详细地址（不包含栋，单元的地址）长度不能超过200")
    @TableField(value = "detail_address", condition = LIKE)
    @Excel(name = "详细地址（不包含栋，单元的地址）")
    private String detailAddress;

    /**
     * 包括栋单元的详细地址
     */
    @ApiModelProperty(value = "包括栋单元的详细地址")
    @Length(max = 200, message = "包括栋单元的详细地址长度不能超过200")
    @TableField(value = "more_detail_address", condition = LIKE)
    @Excel(name = "包括栋单元的详细地址")
    private String moreDetailAddress;

    /**
     * 0表示农村，1表示城市
     */
    @ApiModelProperty(value = "0表示农村，1表示城市")
    @TableField("flag")
    @Excel(name = "0表示农村，1表示城市")
    private Integer flag;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("data_status")
    @Excel(name = "状态")
    private Integer dataStatus;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    @TableField("delete_user")
    @Excel(name = "删除人")
    private Long deleteUser;

    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    @TableField("delete_status")
    private Integer deleteStatus;
    @Builder
    public BatchDetailedAddress(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                                Long communityId, Integer building, Integer unit, String detailAddress, String moreDetailAddress,
                                Integer flag, Integer dataStatus, LocalDateTime deleteTime, Long deleteUser,Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.communityId = communityId;
        this.building = building;
        this.unit = unit;
        this.detailAddress = detailAddress;
        this.moreDetailAddress = moreDetailAddress;
        this.flag = flag;
        this.dataStatus = dataStatus;
        this.deleteTime = deleteTime;
        this.deleteUser = deleteUser;
        this.deleteStatus=deleteStatus;
    }

}
