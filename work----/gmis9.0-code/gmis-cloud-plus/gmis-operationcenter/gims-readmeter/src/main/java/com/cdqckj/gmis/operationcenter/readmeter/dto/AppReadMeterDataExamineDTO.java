package com.cdqckj.gmis.operationcenter.readmeter.dto;

import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 实体类
 * 抄表数据
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
@Validated
@ApiModel(value = "AppReadMeterDataExamineDTO", description = "抄表数据审核dto")
public class AppReadMeterDataExamineDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 抄表数据ids
     */
    @ApiModelProperty(value = "抄表数据ids")
    @NotEmpty(message = "抄表数据ids必须有")
    private List<Long> readMeterDataIds;


    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态TO_BE_REVIEWED --待审核 SUBMIT_FOR_REVIEW 提审,APPROVED(\"审核通过（待结算） REVIEW_REJECTED(\"审核驳回\")")
    @NotNull(message = "必须传入流程状态")
    private ProcessEnum processStatus;


}
