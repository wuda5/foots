package com.cdqckj.gmis.sms.dto;

import java.time.LocalDateTime;
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
 * 短信签名
 * </p>
 *
 * @author gmis
 * @since 2020-08-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SignPageDTO", description = "短信签名")
public class SignPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务器签名返回的id")
    private Long signId;

    /**
     * 签名名称
     */
    @ApiModelProperty(value = "签名名称")
    @NotEmpty(message = "签名名称不能为空")
    @Length(max = 20, message = "签名名称长度不能超过20")
    private String signName;
    /**
     *  签名类型 0：公司（0，1，2，3）。 1：APP
     */
    @ApiModelProperty(value = " 签名类型 0：公司（0，1，2，3）。 1：APP")
    @NotNull(message = " 签名类型 0：公司（0，1，2，3）。 1：APP不能为空")
    private Integer signType;
    /**
     * 证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP
     */
    @ApiModelProperty(value = "证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP")
    @NotNull(message = "证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP不能为空")
    private Integer documentType;
    /**
     * 是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信
     */
    @ApiModelProperty(value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信")
    @NotNull(message = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信不能为空")
    private Integer international;
    /**
     * 签名用途： 0：自用。 1：他用。
     */
    @ApiModelProperty(value = "签名用途： 0：自用。 1：他用。")
    @NotNull(message = "签名用途： 0：自用。 1：他用。不能为空")
    private Integer usedMethod;
    /**
     * 签名审核状态
     */
    @ApiModelProperty(value = "签名审核状态")
    private Integer signStatus;
    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer deleteStatus;

    /**
     * 审核未通过原因
     */
    @ApiModelProperty(value = "审核未通过原因")
    @Length(max = 50, message = "审核未通过原因长度不能超过20")
    private String reviewReply;

}
