package com.cdqckj.gmis.securityed.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 安检计划记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
public interface SecurityCheckRecordService extends SuperService<SecurityCheckRecord> {
    /*新增安检计划
    * */
    Boolean saveSecurityCheckRecord(List<SecurityCheckRecord> securityCheckRecord);

    /*审核
    * */
    Integer approvaledSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params);

    /*驳回
    * */
    Integer rejectSecurityCheckRecord(SecurityCheckRecord params);

    /*结束流程
    * */
    Integer endSecurityCheckRecord(SecurityCheckRecord params);

    /*派单更改安检状态信息和新增安检流程信息
    * */
    Integer leafletSecurityCheckRecord(SecurityCheckRecord params);

    Integer giveOrder(SecurityCheckRecord params);

    Integer completeOrder(SecurityCheckRecord params);
}
