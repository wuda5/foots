package com.cdqckj.gmis.node.dto;

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
 * 节点类型管理
 * </p>
 *
 * @author gmis
 * @since 2020-07-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "UseNodeTypeSaveDTO", description = "节点类型管理")
public class UseNodeTypeSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long nodeFactoryId;
    /**
     * 型号ID
     */
    @ApiModelProperty(value = "型号ID")
    private Long nodeTypeId;
    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String nodeFacotryName;
    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 50, message = "型号名称长度不能超过50")
    private String nodeName;
    /**
     * 启用
     *             禁用
     */
    @ApiModelProperty(value = "启用")
    private Integer useStatus;

}
