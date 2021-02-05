package com.cdqckj.gmis.bizcenter.card.dto;

import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类
 * 补换卡信息加载
 * </p>
 *
 * @author tp
 * @since 2020-11-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class CardRepLoadDTO extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 气表使用信息
     */
    @ApiModelProperty(value = "气表使用信息")
    GasMeterInfo gasMeterInfo;

    /**
     * 补卡信息
     */
    @ApiModelProperty(value = "补卡信息")
    CardRepRecord cardRepRecord;

}
