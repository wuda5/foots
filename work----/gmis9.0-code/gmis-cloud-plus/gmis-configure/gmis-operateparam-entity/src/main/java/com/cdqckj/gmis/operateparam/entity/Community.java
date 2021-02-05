package com.cdqckj.gmis.operateparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-22
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_community")
@ApiModel(value = "Community", description = "")
@AllArgsConstructor
public class Community extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    @Length(max = 32, message = "公司ID长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司ID")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;
    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    @TableField(value = "street_id", condition = LIKE)
    @Excel(name = "街道ID")
    private Long streetId;

    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称")
    @Length(max = 50, message = "小区名称长度不能超过50")
    @TableField(value = "community_name", condition = LIKE)
    @Excel(name = "小区名称")
    private String communityName;

    /**
     * 小区地址
     */
    @ApiModelProperty(value = "小区地址")
    @Length(max = 100, message = "小区地址长度不能超过100")
    @TableField(value = "community_address", condition = LIKE)
    @Excel(name = "小区地址")
    private String communityAddress;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    @Excel(name = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /**
     * 表数
     */
    @ApiModelProperty(value = "表数")
    @TableField("meter_count")
    @Excel(name = "表数")
    private Integer meterCount;

    /**
     * 已拆数
     */
    @ApiModelProperty(value = "已拆数")
    @TableField("dismantle_count")
    @Excel(name = "已拆数")
    private Integer dismantleCount;

    /**
     * 状态（1-生效，0-无效）
     */
    @ApiModelProperty(value = "状态（1-生效，0-无效）")
    @TableField("data_status")
    @Excel(name = "状态（1-生效，0-无效）")
    private Integer dataStatus;
    /**
     * 状态（0-生效，1-删除）
     */
    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    @TableField("delete_status")
    private Integer deleteStatus;


    @Builder
    public Community(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long streetId, String communityName, String communityAddress,
                    BigDecimal longitude, BigDecimal latitude, Integer meterCount, Integer dismantleCount, Integer dataStatus
                     ,Integer deleteStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.streetId = streetId;
        this.communityName = communityName;
        this.communityAddress = communityAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.meterCount = meterCount;
        this.dismantleCount = dismantleCount;
        this.dataStatus = dataStatus;
        this.deleteStatus =deleteStatus;
    }

}
