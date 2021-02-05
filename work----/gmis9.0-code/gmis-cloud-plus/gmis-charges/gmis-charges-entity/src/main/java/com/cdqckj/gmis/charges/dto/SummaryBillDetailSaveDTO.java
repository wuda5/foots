package com.cdqckj.gmis.charges.dto;

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
 * @author songyz
 * @since 2020-12-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SummaryBillDetailSaveDTO", description = "")
public class SummaryBillDetailSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 扎帐来源ID
     */
    @ApiModelProperty(value = "扎帐来源ID")
    private Long sourceId;
    /**
     * 扎帐来源类型
     */
    @ApiModelProperty(value = "扎帐来源类型")
    private Integer sourceType;
    /**
     * 银行扎帐ID转账金额
     */
    @ApiModelProperty(value = "银行扎帐ID转账金额")
    private Long summaryBillId;
    /**
     * 操作员编号
     */
    @ApiModelProperty(value = "操作员编号")
    @Length(max = 32, message = "操作员编号长度不能超过32")
    private String operatorNo;
    /**
     * 扎帐日期
     */
    @ApiModelProperty(value = "扎帐日期")
    @Length(max = 8, message = "扎帐日期长度不能超过8")
    private String summaryBillDate;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;

}
