package com.cdqckj.gmis.statistics.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-11-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoiceDayPageDTO", description = "")
public class InvoiceDayPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @NotEmpty(message = "公司CODE不能为空")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    private Long createUserId;
    /**
     * 发票的数量
     */
    @ApiModelProperty(value = "发票的数量")
    @NotNull(message = "发票的数量不能为空")
    private Integer amount;
    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @NotNull(message = "合计金额不能为空")
    private BigDecimal totalAmount;
    /**
     * 合计税额
     */
    @ApiModelProperty(value = "合计税额")
    @NotNull(message = "合计税额不能为空")
    private BigDecimal totalTax;
    /**
     * 收费的时间是哪一天
     */
    @ApiModelProperty(value = "收费的时间是哪一天")
    @NotEmpty(message = "收费的时间是哪一天不能为空")
    @Length(max = 10, message = "收费的时间是哪一天长度不能超过10")
    private String createDay;
    /**
     * 发票种类
     *             0红票
     *             1蓝票
     *             2废票
     */
    @ApiModelProperty(value = "发票种类")
    @NotEmpty(message = "发票种类不能为空")
    @Length(max = 10, message = "发票种类长度不能超过10")
    private String invoiceKind;
    /**
     * 发票类型
     *             007普票
     *             004专票
     *             026电票
     */
    @ApiModelProperty(value = "发票类型")
    @NotEmpty(message = "发票类型不能为空")
    @Length(max = 30, message = "发票类型长度不能超过30")
    private String invoiceType;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    protected String label;

    @ApiModelProperty(value = "父ID")
    protected Long parentId;

    @ApiModelProperty(value = "排序号")
    protected Integer sortValue;
}
