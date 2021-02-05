package com.cdqckj.gmis.gasmeter.dto;

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
 * 气表厂家
 * </p>
 *
 * @author gmis
 * @since 2020-08-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterFactorySaveDTO", description = "气表厂家")
public class GasMeterFactorySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @NotEmpty(message = "厂家名称不能为空")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 厂家编号
     */
    @ApiModelProperty(value = "厂家编号")
    @NotEmpty(message = "厂家编号不能为空")
    @Length(max = 10, message = "厂家编号长度不能超过10")
    private String gasMeterFactoryCode;
    /**
     * 厂家地址
     */
    @ApiModelProperty(value = "厂家地址")
    @NotEmpty(message = "厂家地址不能为空")
    @Length(max = 100, message = "厂家地址长度不能超过100")
    private String gasMeterFactoryAddress;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @Length(max = 11, message = "请输入正确的电话号码")
    private String telphone;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 10, message = "联系人长度不能超过10")
    private String contacts;
    /**
     * 传真号码
     */
    @ApiModelProperty(value = "传真号码")
    @Length(max = 20, message = "传真号码长度不能超过20")
    private String faxNumber;
    /**
     * 经理
     */
    @ApiModelProperty(value = "经理")
    @Length(max = 10, message = "经理长度不能超过10")
    private String manager;
    /**
     * 税务登记号
     */
    @ApiModelProperty(value = "税务登记号")
    @Length(max = 30, message = "税务登记号长度不能超过30")
    private String taxRegistrationNo;
    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行")
    @Length(max = 20, message = "开户银行长度不能超过20")
    private String bank;
    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 20, message = "银行账号长度不能超过20")
    private String bankAccount;
    /**
     * 普表
     *  ic卡智能燃气表
     *  物联网表
     *  流量计(普表)
     *  流量 计(ic卡表)
     *  流量计(物联网表)
     * 
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 100, message = "普表长度不能超过100")
    private String gasMeterType;
    /**
     * 厂家状态：（0：启用；1：禁用）
     */
    @ApiModelProperty(value = "厂家状态：（1：启用；0：禁用）")
    @NotNull(message = "厂家状态：（1：启用；0：禁用）不能为空")
    private Integer gasMeterFactoryStatus;
    /**
     * 兼容参数
     */
    @ApiModelProperty(value = "兼容参数")
    @Length(max = 1000, message = "兼容参数长度不能超过1000")
    private String compatibleParameter;
    /**
     * 删除标识（0：存在；1-删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1-删除）")
    private Integer deleteStatus;

}
