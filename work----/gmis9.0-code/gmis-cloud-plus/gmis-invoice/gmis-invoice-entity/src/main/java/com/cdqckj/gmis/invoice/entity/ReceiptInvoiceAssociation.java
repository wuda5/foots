package com.cdqckj.gmis.invoice.entity;

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
 * 
 * </p>
 *
 * @author songyz
 * @since 2020-09-09
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_receipt_invoice_association")
@ApiModel(value = "ReceiptInvoiceAssociation", description = "")
@AllArgsConstructor
public class ReceiptInvoiceAssociation extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 票据ID
     */
    @ApiModelProperty(value = "票据ID")
    @TableField("receipt_id")
    @Excel(name = "票据ID")
    private Long receiptId;

    /**
     * 发票ID
     */
    @ApiModelProperty(value = "发票ID")
    @TableField("invoice_id")
    @Excel(name = "发票ID")
    private Long invoiceId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("state")
    @Excel(name = "状态")
    private Integer state;


    @Builder
    public ReceiptInvoiceAssociation(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    Long receiptId, Long invoiceId, Integer state) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.receiptId = receiptId;
        this.invoiceId = invoiceId;
        this.state = state;
    }

}
