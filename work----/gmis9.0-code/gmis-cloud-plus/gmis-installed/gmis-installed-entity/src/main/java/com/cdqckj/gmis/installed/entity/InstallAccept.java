package com.cdqckj.gmis.installed.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 报装验收信息结果
 * </p>
 *
 * @author tp
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_install_accept")
@ApiModel(value = "InstallAccept", description = "报装验收信息结果")
@AllArgsConstructor
public class InstallAccept extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 32, message = "报装编号长度不能超过32")
    @TableField(value = "install_number", condition = LIKE)
    @Excel(name = "报装编号")
    private String installNumber;

    /**
     * 验收意见，通过或不通过
     */
    @ApiModelProperty(value = "验收意见，通过或不通过")
    @TableField("accept_suggest")
    @Excel(name = "验收意见，通过或不通过", replace = {"是_true", "否_false", "_null"})
    private Boolean acceptSuggest;

    /**
     * 验收结论，文本
     */
    @ApiModelProperty(value = "验收结论，文本")
    @Length(max = 65535, message = "验收结论，文本长度不能超过65535")
    @TableField("result_text")
    @Excel(name = "验收结论，文本")
    private String resultText;

    /**
     * 验收时间
     */
    @ApiModelProperty(value = "验收时间")
    @TableField("accept_time")
    @Excel(name = "验收时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime acceptTime;

    /**
     * 验收附件地址
     * 
     */
    @ApiModelProperty(value = "验收附件地址")
    @Length(max = 255, message = "验收附件地址长度不能超过255")
    @TableField(value = "accept_url", condition = LIKE)
    @Excel(name = "验收附件地址")
    private String acceptUrl;






    @Builder
    public InstallAccept(Long id, 
                    String installNumber, Boolean acceptSuggest, String resultText, LocalDateTime acceptTime, String acceptUrl, 
                    LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser) {
        this.id = id;
        this.installNumber = installNumber;
        this.acceptSuggest = acceptSuggest;
        this.resultText = resultText;
        this.acceptTime = acceptTime;
        this.acceptUrl = acceptUrl;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

}
