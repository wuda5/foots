package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.service.BusinessHallQuotaRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/businessHallQuotaRecord")
@Api(value = "BusinessHallQuotaRecord", tags = "营业厅配额")
// @PreAuth(replace = "businessHallQuotaRecord:")
public class BusinessHallQuotaRecordController extends SuperController<BusinessHallQuotaRecordService, Long, BusinessHallQuotaRecord, BusinessHallQuotaRecordPageDTO, BusinessHallQuotaRecordSaveDTO, BusinessHallQuotaRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<BusinessHallQuotaRecord> businessHallQuotaRecordList = list.stream().map((map) -> {
            BusinessHallQuotaRecord businessHallQuotaRecord = BusinessHallQuotaRecord.builder().build();
            //TODO 请在这里完成转换
            return businessHallQuotaRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(businessHallQuotaRecordList));
    }
}
