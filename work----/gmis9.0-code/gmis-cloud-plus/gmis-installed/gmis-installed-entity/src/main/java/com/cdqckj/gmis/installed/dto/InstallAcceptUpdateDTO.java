package com.cdqckj.gmis.installed.dto;

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
 * 报装验收信息结果
 * </p>
 *
 * @author tp
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InstallAcceptUpdateDTO", description = "报装验收信息结果")
public class InstallAcceptUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    private String installNumber;
    /**
     * 验收意见，通过或不通过
     */
    @ApiModelProperty(value = "验收意见，通过或不通过")
    private Boolean acceptSuggest;
    /**
     * 验收结论，文本
     */
    @ApiModelProperty(value = "验收结论，文本")
    @Length(max = 65535, message = "验收结论，文本长度不能超过65,535")
    private String resultText;
    /**
     * 验收时间
     */
    @ApiModelProperty(value = "验收时间")
    private LocalDateTime acceptTime;
    /**
     * 验收附件地址
     * 
     */
    @ApiModelProperty(value = "验收附件地址")
    @Length(max = 255, message = "验收附件地址长度不能超过255")
    private String acceptUrl;
}
