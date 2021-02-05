package com.cdqckj.gmis.card.dto;

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
 * 卡表价格方案记录
 * </p>
 *
 * @author tp
 * @since 2021-01-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CardPriceSchemeUpdateDTO", description = "卡表价格方案记录")
public class CardPriceSchemeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    private String gasMeterCode;
    /**
     * 当前写入方案
     */
    @ApiModelProperty(value = "当前写入方案")
    @NotNull(message = "当前写入方案不能为空")
    private Long writeCardPriceId;
    /**
     * 当前上表方案
     */
    @ApiModelProperty(value = "当前上表方案")
    @NotNull(message = "当前上表方案不能为空")
    private Long intoMeterPriceId;
    /**
     * 预调价写入方案
     */
    @ApiModelProperty(value = "预调价写入方案")
    @NotNull(message = "预调价写入方案不能为空")
    private Long preWriteCardPriceId;
    /**
     * 预调价上表方案
     */
    @ApiModelProperty(value = "预调价上表方案")
    @NotNull(message = "预调价上表方案不能为空")
    private Long preIntoMeterPriceId;
}
