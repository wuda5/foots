package com.cdqckj.gmis.systemparam.entity.vo;

import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 实体类
 * 模板类型配置表
 * </p>
 *
 * @author gmis
 * @since 2020-07-01
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "TemplateVo", description = "模板类型配置表")
@AllArgsConstructor
public class TemplateVo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子集合")
    private List<TemplateVo> children = new ArrayList<>();

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
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
    private String orgName;

    /**
     * 模板类型编码
     */
    @ApiModelProperty(value = "模板类型编码")
    private String templateTypeCode;

    /**
     * 模板类型名称
     */
    @ApiModelProperty(value = "模板类型名称")
    private String templateTypeName;

    /**
     * 模板类型描述
     */
    @ApiModelProperty(value = "模板类型描述")
    private String templateTypeDescribe;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer templateStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 1：存在 0：删除")
    private Integer deleteStatus;

}
