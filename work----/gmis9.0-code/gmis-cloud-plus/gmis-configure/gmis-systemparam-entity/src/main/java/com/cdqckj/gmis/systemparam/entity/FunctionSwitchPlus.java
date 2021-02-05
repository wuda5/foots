package com.cdqckj.gmis.systemparam.entity;

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
 * 功能项配置plus
 * </p>
 *
 * @author gmis
 * @since 2020-12-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_function_switch_plus")
@ApiModel(value = "FunctionSwitchPlus", description = "功能项配置plus")
@AllArgsConstructor
public class FunctionSwitchPlus extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @ApiModelProperty(value = "key")
    @NotEmpty(message = "key不能为空")
    @Length(max = 12, message = "key长度不能超过12")
    @TableField(value = "item", condition = LIKE)
    @Excel(name = "key")
    private String item;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    @NotEmpty(message = "值不能为空")
    @Length(max = 50, message = "值长度不能超过50")
    @TableField(value = "value", condition = LIKE)
    @Excel(name = "值")
    private String value;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    @Length(max = 200, message = "描述长度不能超过200")
    @TableField(value = "desc_value", condition = LIKE)
    @Excel(name = "描述")
    private String descValue;


    @Builder
    public FunctionSwitchPlus(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String item, String value, String descValue) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.item = item;
        this.value = value;
        this.descValue = descValue;
    }

}
