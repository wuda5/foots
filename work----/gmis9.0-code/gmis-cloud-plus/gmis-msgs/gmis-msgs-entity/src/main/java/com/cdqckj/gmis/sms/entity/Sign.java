package com.cdqckj.gmis.sms.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.cdqckj.gmis.base.entity.Entity;
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
 * 短信签名
 * </p>
 *
 * @author gmis
 * @since 2020-08-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sms_sign")
@ApiModel(value = "Sign", description = "短信签名")
@AllArgsConstructor
public class Sign extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "签名返回id")
    @TableField("sign_id")
    @Excel(name = "签名返回id")
    private Long signId;

    /**
     * 签名名称
     */
    @ApiModelProperty(value = "签名名称")
    @NotEmpty(message = "签名名称不能为空")
    @Length(max = 20, message = "签名名称长度不能超过20")
    @TableField(value = "sign_name", condition = LIKE)
    @Excel(name = "签名名称")
    private String signName;

    /**
     *  签名类型 0：公司（0，1，2，3）。 1：APP
     */
    @ApiModelProperty(value = " 签名类型 0：公司（0，1，2，3）。 1：APP")
    @NotNull(message = " 签名类型 0：公司（0，1，2，3）。 1：APP不能为空")
    @TableField("sign_type")
    @Excel(name = " 签名类型 0：公司（0，1，2，3）。 1：APP")
    private Integer signType;

    /**
     * 证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP
     */
    @ApiModelProperty(value = "证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP")
    @NotNull(message = "证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP不能为空")
    @TableField("document_type")
    @Excel(name = "证明类型  0：三证合一。 1：企业营业执照。 2：组织机构代码证书。 3：社会信用代码证书。 4：应用后台管理截图（个人开发APP")
    private Integer documentType;

    /**
     * 是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信
     */
    @ApiModelProperty(value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信")
    @NotNull(message = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信不能为空")
    @TableField("international")
    @Excel(name = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信")
    private Integer international;

    /**
     * 签名用途： 0：自用。 1：他用。
     */
    @ApiModelProperty(value = "签名用途： 0：自用。 1：他用。")
    @NotNull(message = "签名用途： 0：自用。 1：他用。不能为空")
    @TableField("used_method")
    @Excel(name = "签名用途： 0：自用。 1：他用。")
    private Integer usedMethod;

    /**
     * 签名审核状态
     */
    @ApiModelProperty(value = "签名审核状态")
    @TableField("sign_status")
    @Excel(name = "签名审核状态")
    private Integer signStatus;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    @TableField("delete_status")
    @Excel(name = "删除标识")
    private Integer deleteStatus;

    /**
     * 审核未通过原因
     */
    @ApiModelProperty(value = "审核未通过原因")
    //@NotEmpty(message = "审核未通过原因")
    @Length(max = 50, message = "审核未通过原因长度不能超过20")
    @TableField(value = "review_reply", condition = LIKE, updateStrategy = FieldStrategy.IGNORED)
    @Excel(name = "审核未通过原因")
    private String reviewReply;


    @Builder
    public Sign(Long id,Long signId,Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String signName, Integer signType, Integer documentType, Integer international, Integer usedMethod, 
                    Integer signStatus, Integer deleteStatus, String reviewReply) {
        this.id = id;
        this.signId = signId;
        this.signName = signName;
        this.signType = signType;
        this.documentType = documentType;
        this.international = international;
        this.usedMethod = usedMethod;
        this.signStatus = signStatus;
        this.deleteStatus = deleteStatus;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.reviewReply = reviewReply;
    }

}
