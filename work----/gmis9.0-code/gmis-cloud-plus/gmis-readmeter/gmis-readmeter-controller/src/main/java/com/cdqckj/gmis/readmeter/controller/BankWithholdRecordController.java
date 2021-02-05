package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.readmeter.entity.BankWithholdRecord;
import com.cdqckj.gmis.readmeter.dto.BankWithholdRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.BankWithholdRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.BankWithholdRecordPageDTO;
import com.cdqckj.gmis.readmeter.service.BankWithholdRecordService;
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
 * 银行代扣记录
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bankWithholdRecord")
@Api(value = "BankWithholdRecord", tags = "银行代扣记录")
@PreAuth(replace = "bankWithholdRecord:")
public class BankWithholdRecordController extends SuperController<BankWithholdRecordService, Long, BankWithholdRecord, BankWithholdRecordPageDTO, BankWithholdRecordSaveDTO, BankWithholdRecordUpdateDTO> {


}
