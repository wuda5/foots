package com.cdqckj.gmis.card.entity;

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
 * 卡表价格方案记录
 * </p>
 *
 * @author tp
 * @since 2021-01-12
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_card_price_scheme")
@ApiModel(value = "CardPriceScheme", description = "卡表价格方案记录")
@AllArgsConstructor
public class CardPriceScheme extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编码")
    private String gasMeterCode;

    /**
     * 当前写入方案
     */
    @ApiModelProperty(value = "当前写入方案")
    @NotNull(message = "当前写入方案不能为空")
    @TableField("write_card_price_id")
    @Excel(name = "当前写入方案")
    private Long writeCardPriceId;

    /**
     * 当前上表方案
     */
    @ApiModelProperty(value = "当前上表方案")
    @NotNull(message = "当前上表方案不能为空")
    @TableField("into_meter_price_id")
    @Excel(name = "当前上表方案")
    private Long intoMeterPriceId;

    /**
     * 预调价写入方案
     */
    @ApiModelProperty(value = "预调价写入方案")
    @NotNull(message = "预调价写入方案不能为空")
    @TableField("pre_write_card_price_id")
    @Excel(name = "预调价写入方案")
    private Long preWriteCardPriceId;

    /**
     * 预调价上表方案
     */
    @ApiModelProperty(value = "预调价上表方案")
    @NotNull(message = "预调价上表方案不能为空")
    @TableField("pre_into_meter_price_id")
    @Excel(name = "预调价上表方案")
    private Long preIntoMeterPriceId;


    @Builder
    public CardPriceScheme(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String gasMeterCode, Long writeCardPriceId, Long intoMeterPriceId, Long preWriteCardPriceId, Long preIntoMeterPriceId) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.gasMeterCode = gasMeterCode;
        this.writeCardPriceId = writeCardPriceId;
        this.intoMeterPriceId = intoMeterPriceId;
        this.preWriteCardPriceId = preWriteCardPriceId;
        this.preIntoMeterPriceId = preIntoMeterPriceId;
    }

}
