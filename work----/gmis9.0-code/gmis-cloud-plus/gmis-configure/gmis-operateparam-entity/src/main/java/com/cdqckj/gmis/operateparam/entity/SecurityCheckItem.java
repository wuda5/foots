package com.cdqckj.gmis.operateparam.entity;

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
 * 安检子项配置
 * </p>
 *
 * @author gmis
 * @since 2020-11-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_security_check_item")
@ApiModel(value = "SecurityCheckItem", description = "安检子项配置")
@AllArgsConstructor
public class SecurityCheckItem extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 子项名称
     */
    @ApiModelProperty(value = "子项名称")
    @Length(max = 255, message = "子项名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "子项名称")
    private String name;

    /**
     * 安检项编码
     */
    @ApiModelProperty(value = "安检项编码")
    @Length(max = 255, message = "安检项编码长度不能超过255")
    @TableField(value = "security_code", condition = LIKE)
    @Excel(name = "安检项编码")
    private String securityCode;

    /**
     * 安检项名称
     */
    @ApiModelProperty(value = "安检项名称")
    @Length(max = 255, message = "安检项名称长度不能超过255")
    @TableField(value = "security_name", condition = LIKE)
    @Excel(name = "安检项名称")
    private String securityName;

    /**
     * 隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)
     */
    @ApiModelProperty(value = "隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)")
    @TableField("danger_level")
    @Excel(name = "隐患级别(1-一般事故隐患，2-中度事故隐患，0-重度事故隐患)")
    private Integer dangerLevel;

    /**
     * 数据状态(1-有效，0-无效)
     */
    @ApiModelProperty(value = "数据状态(1-有效，0-无效)")
    @TableField("data_status")
    @Excel(name = "数据状态(1-有效，0-无效)")
    private Integer dataStatus;

    /**
     * 删除状态(1-删除，0-有效)
     */
    @ApiModelProperty(value = "删除状态(1-删除，0-有效)")
    @TableField("delete_status")
    @Excel(name = "删除状态(1-删除，0-有效)")
    private Integer deleteStatus;


    @Builder
    public SecurityCheckItem(Long id, Long updateUser, LocalDateTime updateTime, Long createUser, LocalDateTime createTime, 
                    String name, String securityCode, String securityName, Integer dangerLevel, Integer dataStatus, Integer deleteStatus) {
        this.id = id;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.createTime = createTime;
        this.name = name;
        this.securityCode = securityCode;
        this.securityName = securityName;
        this.dangerLevel = dangerLevel;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
    }

}
