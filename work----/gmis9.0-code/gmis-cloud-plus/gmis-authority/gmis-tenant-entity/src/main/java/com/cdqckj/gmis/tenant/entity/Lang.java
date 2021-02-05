package com.cdqckj.gmis.tenant.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-08
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("c_common_lang")
@ApiModel(value = "Lang", description = "")
@AllArgsConstructor
public class Lang {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    protected Long id;

    @ApiModelProperty(value = "")
    @Length(max = 255, message = "长度不能超过255")
    @TableField(value = "lang_key", condition = LIKE)
    @Excel(name = "")
    private String langKey;

    @ApiModelProperty(value = "")
    @Length(max = 255, message = "长度不能超过255")
    @TableField(value = "lang_value", condition = LIKE)
    @Excel(name = "")
    private String langValue;

    /**
     * 语言类型(1 中文 2 英文)
     */
    @ApiModelProperty(value = "语言类型(1 中文 2 英文)")
    @TableField("lang_type")
    @Excel(name = "语言类型(1 中文 2 英文)")
    private Integer langType;

    @ApiModelProperty(value = "数据类型(0 通知消息 1 菜单 2 其他)")
    @TableField("data_type")
    @Excel(name = "数据类型(0 通知消息 1 菜单 2 其他)")
    private Integer dataType;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.DEFAULT)
    protected LocalDateTime createTime;

    @Builder
    public Lang(Long id, LocalDateTime createTime,
                String langKey, String langValue, Integer langType) {
        this.id = id;
        this.createTime = createTime;
        this.langKey = langKey;
        this.langValue = langValue;
        this.langType = langType;
    }

}
