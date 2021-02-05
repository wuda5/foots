package com.cdqckj.gmis.readmeter.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
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
 * 抄表册
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReadMeterBookPageDTO", description = "抄表册")
public class ReadMeterBookPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 所属区域id
     */
    @ApiModelProperty(value = "所属区域id")
    private Long communityId;

    /**
     * 所属区域名称
     */
    @ApiModelProperty(value = "所属区域名称")
    private String communityName;
    /**
     * 抄表册编号
     */
    @ApiModelProperty(value = "抄表册编号")
    @Length(max = 10, message = "抄表册编号长度不能超过10")
    private String bookCode;
    /**
     * 抄表册名称
     */
    @ApiModelProperty(value = "抄表册名称")
    @Length(max = 30, message = "抄表册名称长度不能超过30")
    private String bookName;
    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    private Long readMeterUser;
    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Length(max = 100, message = "抄表员名称长度不能超过100")
    private String readMeterUserName;
    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    private Integer totalReadMeterCount;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer bookStatus;
    /**
     * 被抄表计划引用次数
     */
    @ApiModelProperty(value = "被未完成抄表计划引用次数")
    private Integer citedCount;
    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    @Length(max = 100, message = "说明长度不能超过100")
    private String remark;

    @ApiModelProperty(value = "删除标识 0：存在 1：删除")
    private Integer deleteStatus;

}
