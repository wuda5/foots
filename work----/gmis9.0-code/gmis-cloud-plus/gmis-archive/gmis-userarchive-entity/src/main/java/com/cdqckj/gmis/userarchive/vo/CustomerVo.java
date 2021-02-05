package com.cdqckj.gmis.userarchive.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.common.domain.excell.FailNumInfo;
import com.cdqckj.gmis.common.domain.excell.RowFailDescribe;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import com.google.common.base.Joiner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Customer", description = "")
public class CustomerVo implements RowFailDescribe {
    /*
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称(必填)")
    @ExcelSelf(name = "客户名称(必填),cutomerName")
    private String customerName;

    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    @Length(max = 100, message = "居民 商福 工业 低保长度不能超过100")
    @TableField(value = "customer_type_name", condition = LIKE)
    @Excel(name = "客户类型名称(必填)")
    @ExcelSelf(name = "客户类型名称(必填),customerTypeName")
    private String customerTypeName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField("sex")
    @Excel(name = "性别(必填)")
    @ExcelSelf(name = "性别(必填),sex")
    private String sex;

    @ApiModelProperty(value = "身份证号码")
    @Excel(name = "身份证号码(必填)")
    @Length(max = 30, message = "身份证长度不能超过16")
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话(必填)")
    @ExcelSelf(name = "联系电话(必填),telphone")
    private String telphone;

    /**
     * 家庭住址/单位地址
     */
    @ApiModelProperty(value = "家庭住址/单位地址")
    @Length(max = 100, message = "家庭住址/单位地址长度不能超过100")
    @TableField(value = "contact_address", condition = LIKE)
    @Excel(name = "家庭住址/单位地址")
    @ExcelSelf(name = "家庭住址/单位地址,contactAddress")
    private String contactAddress;

    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "长度不能超过100")
    @Excel(name = "备注")
    @TableField(value = "remark", condition = LIKE)
    private String remark;

    private String failValue;

    @Override
    public void setMsg(List<FailNumInfo> infoList) {
        failValue = Joiner.on(",").join(infoList.stream().map(FailNumInfo::getFailDescribe).collect(Collectors.toList()));
    }
}
