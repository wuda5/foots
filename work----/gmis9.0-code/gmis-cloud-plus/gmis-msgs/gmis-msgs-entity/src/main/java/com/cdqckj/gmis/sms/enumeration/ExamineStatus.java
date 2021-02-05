package com.cdqckj.gmis.sms.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ExamineStatus", description = "审核状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExamineStatus {
    /**
     * 通过
     */
    APPROVED(0, "通过"),
    /**
     * 未通过
     */
    AUDIT_FAILED(-1, "未通过"),
    /**
     * 审核中
     */
    UNDER_REVIEW(1, "审核中");

    private int code;
    private String description;
}
