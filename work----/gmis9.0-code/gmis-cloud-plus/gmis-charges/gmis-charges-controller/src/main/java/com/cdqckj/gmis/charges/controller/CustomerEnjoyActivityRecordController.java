package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.CustomerEnjoyActivityRecordPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerEnjoyActivityRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerEnjoyActivityRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import com.cdqckj.gmis.charges.service.CustomerEnjoyActivityRecordService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 客户享受活动明细记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerEnjoyActivityRecord")
@Api(value = "CustomerEnjoyActivityRecord", tags = "客户享受活动明细记录")
//@PreAuth(replace = "customerEnjoyActivityRecord:")
public class CustomerEnjoyActivityRecordController extends SuperController<CustomerEnjoyActivityRecordService, Long, CustomerEnjoyActivityRecord, CustomerEnjoyActivityRecordPageDTO, CustomerEnjoyActivityRecordSaveDTO, CustomerEnjoyActivityRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerEnjoyActivityRecord> customerEnjoyActivityRecordList = list.stream().map((map) -> {
            CustomerEnjoyActivityRecord customerEnjoyActivityRecord = CustomerEnjoyActivityRecord.builder().build();
            //TODO 请在这里完成转换
            return customerEnjoyActivityRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerEnjoyActivityRecordList));
    }
}
