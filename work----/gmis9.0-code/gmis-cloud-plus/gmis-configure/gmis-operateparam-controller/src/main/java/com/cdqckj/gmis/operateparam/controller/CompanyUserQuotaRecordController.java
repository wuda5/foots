package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.service.CompanyUserQuotaRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @date 2020-07-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/companyUserQuotaRecord")
@Api(value = "CompanyUserQuotaRecord", tags = "")
@PreAuth(replace = "companyUserQuotaRecord:")
public class CompanyUserQuotaRecordController extends SuperController<CompanyUserQuotaRecordService, Long, CompanyUserQuotaRecord, CompanyUserQuotaRecordPageDTO, CompanyUserQuotaRecordSaveDTO, CompanyUserQuotaRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CompanyUserQuotaRecord> companyUserQuotaRecordList = list.stream().map((map) -> {
            CompanyUserQuotaRecord companyUserQuotaRecord = CompanyUserQuotaRecord.builder().build();
            //TODO 请在这里完成转换
            return companyUserQuotaRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(companyUserQuotaRecordList));
    }

    @GetMapping(value = "/findBusinessHallUser")
    public R<Map<String, List<BusinessHallVO>>> getUserBusinessHall(){
        return baseService.getUserBusinessHall();
    }

    @GetMapping(value = "/queryRecentRecord")
    public CompanyUserQuotaRecord queryRecentRecord(){
        return baseService.queryRecentRecord();
    }
}
