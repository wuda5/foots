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

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-14
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_street")
@ApiModel(value = "Street", description = "")
@AllArgsConstructor
public class Street extends Entity<Long> {

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
     * 省code
     */
    @ApiModelProperty(value = "省编号")
    @TableField("province_code")
    @Excel(name = "省code")
    private String provinceCode;

    /**
     * 省名称
     */
    @ApiModelProperty(value = "省名称")
    @Length(max = 255, message = "省名称长度不能超过255")
    @TableField(value = "province_name", condition = LIKE)
    @Excel(name = "省名称")
    private String provinceName;

    /**
     * 市code
     */
    @ApiModelProperty(value = "市编号")
    @TableField("city_code")
    @Excel(name = "市ID")
    private String cityCode;

    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    @Length(max = 255, message = "市名称长度不能超过255")
    @TableField(value = "city_name", condition = LIKE)
    @Excel(name = "市名称")
    private String cityName;
    /**
     * 区id
     */
    @ApiModelProperty(value = "区code")
    @TableField("area_code")
    @Excel(name = "区code")
    private String areaCode;
    /**
     * 区名称
     */
    @ApiModelProperty(value = "区名称")
    @Length(max = 50, message = "区名称长度不能超过50")
    @TableField(value = "area_name", condition = LIKE)
    @Excel(name = "区名称")
    private String areaName;



    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称")
    @Length(max = 100, message = "街道名称长度不能超过100")
    @TableField(value = "street_name", condition = LIKE)
    @Excel(name = "街道名称")
    private String streetName;

    /**
     * 状态（1-生效，0-无效）
     */
    @ApiModelProperty(value = "状态（1-生效，0-无效）")
    @TableField("data_status")
    @Excel(name = "状态（1-生效，0-无效）")
    private Integer dataStatus;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除用户id
     */
    @ApiModelProperty(value = "删除用户id")
    @TableField("delete_user")
    @Excel(name = "删除用户id")
    private Long deleteUser;

    /**
     * 状态（0-生效，1-删除）
     */
    @ApiModelProperty(value = "状态（0-生效，1-删除）")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Builder
    public Street(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, String areaCode, String areaName, String cityCode,
                    String cityName, String streetName, Integer dataStatus,String provinceName, String provinceCode,
                  LocalDateTime deleteTime, Long deleteUser,Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.streetName = streetName;
        this.dataStatus = dataStatus;
        this.deleteTime = deleteTime;
        this.deleteUser = deleteUser;
        this.deleteStatus = deleteStatus;
    }

}
